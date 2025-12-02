package com.libreria.beans;

import com.libreria.client.RestClient;
import com.libreria.dto.*;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Managed Bean para la gestión de reportes
 */
@Named
@ViewScoped
public class ReporteBean implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(ReporteBean.class.getName());
    private static final long serialVersionUID = 1L;

    @Inject
    private RestClient restClient;

    // Opciones de reportes disponibles
    private String tipoReporte;
    private List<String> tiposReporte;

    // Parámetros para filtros
    private Date fechaInicio;
    private Date fechaFin;
    private String estadoFiltro;

    @PostConstruct
    public void init() {
        LOGGER.info("Inicializando ReporteBean...");
        try {
            tiposReporte = new ArrayList<>();
            tiposReporte.add("Catálogo de Obras Disponibles");
            tiposReporte.add("Préstamos Activos");
            tiposReporte.add("Multas");
            tiposReporte.add("Usuarios");
            LOGGER.info("ReporteBean inicializado correctamente con " + tiposReporte.size() + " tipos de reportes");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al inicializar ReporteBean", e);
        }
    }

    /**
     * Genera y descarga el reporte en formato PDF
     */
    public void generarReportePDF() {
        if (tipoReporte == null || tipoReporte.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Advertencia", "Debe seleccionar un tipo de reporte"));
            return;
        }

        try {
            byte[] pdfBytes = generarReporte(tipoReporte, "pdf");
            descargarArchivo(pdfBytes, obtenerNombreArchivo(tipoReporte, "pdf"), "application/pdf");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al generar reporte PDF", e);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error", "No se pudo generar el reporte: " + e.getMessage()));
        }
    }

    /**
     * Genera y descarga el reporte en formato Excel
     */
    public void generarReporteExcel() {
        if (tipoReporte == null || tipoReporte.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Advertencia", "Debe seleccionar un tipo de reporte"));
            return;
        }

        try {
            byte[] excelBytes = generarReporte(tipoReporte, "xlsx");
            descargarArchivo(excelBytes, obtenerNombreArchivo(tipoReporte, "xlsx"),
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al generar reporte Excel", e);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error", "No se pudo generar el reporte: " + e.getMessage()));
        }
    }

    /**
     * Genera el reporte según el tipo y formato especificado
     */
    private byte[] generarReporte(String tipo, String formato) throws Exception {
        String archivoJRXML = obtenerArchivoJRXML(tipo);
        Collection<?> datos = obtenerDatosReporte(tipo);

        // Cargar el archivo JRXML
        InputStream inputStream = getClass().getResourceAsStream("/META-INF/resources/reportes/" + archivoJRXML);
        if (inputStream == null) {
            throw new Exception("No se encontró el archivo de reporte: " + archivoJRXML);
        }

        // Compilar el reporte
        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

        // Crear el datasource
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(datos);

        // Parámetros del reporte
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("FECHA_GENERACION", new Date());
        parametros.put("TITULO", tipo);

        if (fechaInicio != null) {
            parametros.put("FECHA_INICIO", fechaInicio);
        }
        if (fechaFin != null) {
            parametros.put("FECHA_FIN", fechaFin);
        }

        // Llenar el reporte
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);

        // Exportar según el formato
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        if ("pdf".equalsIgnoreCase(formato)) {
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        } else if ("xlsx".equalsIgnoreCase(formato)) {
            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));

            SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
            configuration.setDetectCellType(true);
            configuration.setCollapseRowSpan(false);
            exporter.setConfiguration(configuration);

            exporter.exportReport();
        }

        return outputStream.toByteArray();
    }

    /**
     * Obtiene el archivo JRXML correspondiente al tipo de reporte
     */
    private String obtenerArchivoJRXML(String tipo) {
        switch (tipo) {
            case "Catálogo de Obras Disponibles":
                return "Catalogo_de_Obras_Disponibles_nuevo.jrxml";
            case "Préstamos Activos":
                return "Prestamos_activos_nuevo.jrxml";
            case "Multas":
                return "Multas_nuevo.jrxml";
            case "Usuarios":
                return "Usuarios_nuevo.jrxml";
            default:
                return "";
        }
    }

    /**
     * Obtiene los datos para el reporte desde la API REST
     */
    private Collection<?> obtenerDatosReporte(String tipo) throws Exception {
        List<?> datos = new ArrayList<>();

        try {
            switch (tipo) {
                case "Catálogo de Obras Disponibles":
                    List<ObraDTO> obras = restClient.getList("obras", ObraDTO[].class);
                    // Filtrar obras disponibles (las que tienen estado DISPONIBLE)
                    datos = obras.stream()
                            .filter(o -> "DISPONIBLE".equalsIgnoreCase(o.getEstado()))
                            .toList();
                    break;

                case "Préstamos Activos":
                    List<PrestamoDTO> prestamos = restClient.getList("prestamos", PrestamoDTO[].class);
                    // Filtrar préstamos activos
                    datos = prestamos.stream()
                            .filter(p -> "ACTIVO".equalsIgnoreCase(p.getEstado()))
                            .toList();
                    break;

                case "Multas":
                    datos = restClient.getList("multas", MultaDTO[].class);
                    break;

                case "Usuarios":
                    datos = restClient.getList("usuarios", UsuarioDTO[].class);
                    break;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al obtener datos para el reporte: " + tipo, e);
            throw new Exception("Error al obtener datos: " + e.getMessage());
        }

        if (datos == null || datos.isEmpty()) {
            LOGGER.warning("No se encontraron datos para el reporte: " + tipo);
            // Retornar lista vacía en lugar de null
            return new ArrayList<>();
        }

        return datos;
    }

    /**
     * Descarga el archivo generado
     */
    private void descargarArchivo(byte[] contenido, String nombreArchivo, String tipoContenido) throws Exception {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();

        externalContext.responseReset();
        externalContext.setResponseContentType(tipoContenido);
        externalContext.setResponseContentLength(contenido.length);
        externalContext.setResponseHeader("Content-Disposition",
                "attachment; filename=\"" + nombreArchivo + "\"");

        OutputStream outputStream = externalContext.getResponseOutputStream();
        outputStream.write(contenido);
        outputStream.flush();

        facesContext.responseComplete();
    }

    /**
     * Genera el nombre del archivo según el tipo de reporte y formato
     */
    private String obtenerNombreArchivo(String tipo, String formato) {
        String nombre = tipo.replace(" ", "_");
        String fecha = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return nombre + "_" + fecha + "." + formato;
    }

    // Getters y Setters
    public String getTipoReporte() {
        return tipoReporte;
    }

    public void setTipoReporte(String tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    public List<String> getTiposReporte() {
        return tiposReporte;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstadoFiltro() {
        return estadoFiltro;
    }

    public void setEstadoFiltro(String estadoFiltro) {
        this.estadoFiltro = estadoFiltro;
    }
}

