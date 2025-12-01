package com.libreria.beans;

import com.libreria.client.RestClient;
import com.libreria.dto.PrestamoDTO;
import com.libreria.dto.UsuarioDTO;
import com.libreria.dto.EjemplarDTO;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Managed Bean para gestión de Préstamos.
 */
@Named("prestamoBean")
@ViewScoped
public class PrestamoBean implements Serializable {

    @Inject
    private RestClient restClient;

    private List<PrestamoDTO> prestamos;
    private PrestamoDTO prestamo;
    private PrestamoDTO prestamoSeleccionado;
    private String filtro;

    // Listas para selects
    private List<UsuarioDTO> usuarios;
    private List<EjemplarDTO> ejemplares;

    @PostConstruct
    public void init() {
        prestamo = new PrestamoDTO();
        prestamo.setEstado("ACTIVO");
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setFechaDevolucionPrevista(LocalDate.now().plusDays(15));
        prestamo.setMulta(BigDecimal.ZERO);

        cargarPrestamos();
        cargarUsuarios();
        cargarEjemplares();
    }

    // ========== OPERACIONES CRUD ==========

    public void cargarPrestamos() {
        prestamos = restClient.getPrestamos();
    }

    public void cargarUsuarios() {
        usuarios = restClient.getUsuarios();
    }

    public void cargarEjemplares() {
        ejemplares = restClient.getEjemplares();
    }

    public void nuevoPrestamo() {
        prestamo = new PrestamoDTO();
        prestamo.setEstado("ACTIVO");
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setFechaDevolucionPrevista(LocalDate.now().plusDays(15));
        prestamo.setMulta(BigDecimal.ZERO);
    }

    public void guardarPrestamo() {
        try {
            if (prestamo.getId() == null) {
                // Crear nuevo
                PrestamoDTO creado = restClient.createPrestamo(prestamo);
                if (creado != null) {
                    addMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Préstamo registrado correctamente");
                    cargarPrestamos();
                    nuevoPrestamo();
                } else {
                    addMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo registrar el préstamo");
                }
            } else {
                // Actualizar existente
                PrestamoDTO actualizado = restClient.updatePrestamo(prestamo.getId(), prestamo);
                if (actualizado != null) {
                    addMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Préstamo actualizado correctamente");
                    cargarPrestamos();
                    nuevoPrestamo();
                } else {
                    addMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo actualizar el préstamo");
                }
            }
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ocurrió un error: " + e.getMessage());
        }
    }

    public void editarPrestamo(PrestamoDTO prestamo) {
        this.prestamo = new PrestamoDTO();
        this.prestamo.setId(prestamo.getId());
        this.prestamo.setUsuarioId(prestamo.getUsuarioId());
        this.prestamo.setEjemplarId(prestamo.getEjemplarId());
        this.prestamo.setFechaPrestamo(prestamo.getFechaPrestamo());
        this.prestamo.setFechaDevolucionPrevista(prestamo.getFechaDevolucionPrevista());
        this.prestamo.setFechaDevolucionReal(prestamo.getFechaDevolucionReal());
        this.prestamo.setEstado(prestamo.getEstado());
        this.prestamo.setMulta(prestamo.getMulta());
        this.prestamo.setObservaciones(prestamo.getObservaciones());
    }

    public void devolverPrestamo(PrestamoDTO prestamo) {
        this.prestamo = new PrestamoDTO();
        this.prestamo.setId(prestamo.getId());
        this.prestamo.setUsuarioId(prestamo.getUsuarioId());
        this.prestamo.setEjemplarId(prestamo.getEjemplarId());
        this.prestamo.setFechaPrestamo(prestamo.getFechaPrestamo());
        this.prestamo.setFechaDevolucionPrevista(prestamo.getFechaDevolucionPrevista());
        this.prestamo.setFechaDevolucionReal(LocalDate.now());
        this.prestamo.setEstado("DEVUELTO");

        // Calcular multa si hay retraso
        if (prestamo.getFechaDevolucionPrevista() != null) {
            LocalDate hoy = LocalDate.now();
            long diasRetraso = ChronoUnit.DAYS.between(prestamo.getFechaDevolucionPrevista(), hoy);
            if (diasRetraso > 0) {
                // 0.50€ por día de retraso
                this.prestamo.setMulta(new BigDecimal(diasRetraso * 0.50));
                this.prestamo.setObservaciones("Devolución con " + diasRetraso + " días de retraso");
            } else {
                this.prestamo.setMulta(BigDecimal.ZERO);
                this.prestamo.setObservaciones("Devolución a tiempo");
            }
        }

        guardarPrestamo();
    }

    public void eliminarPrestamo(Integer id) {
        try {
            boolean eliminado = restClient.deletePrestamo(id);
            if (eliminado) {
                addMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Préstamo eliminado correctamente");
                cargarPrestamos();
            } else {
                addMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo eliminar el préstamo");
            }
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ocurrió un error: " + e.getMessage());
        }
    }

    public void seleccionarPrestamo(PrestamoDTO prestamo) {
        this.prestamoSeleccionado = prestamo;
    }

    // ========== UTILIDADES ==========

    private void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(severity, summary, detail));
    }

    public List<PrestamoDTO> getPrestamosFiltrados() {
        if (filtro == null || filtro.isEmpty()) {
            return prestamos;
        }
        return prestamos.stream()
                .filter(p -> p.getEstado().toLowerCase().contains(filtro.toLowerCase()))
                .toList();
    }

    public List<PrestamoDTO> getPrestamosActivos() {
        return prestamos != null ? prestamos.stream()
                .filter(p -> "ACTIVO".equals(p.getEstado()))
                .toList() : List.of();
    }

    public String getUsuarioNombre(Integer usuarioId) {
        if (usuarioId != null && usuarios != null) {
            return usuarios.stream()
                    .filter(u -> u.getId().equals(usuarioId))
                    .findFirst()
                    .map(u -> u.getNombre() + " " + u.getApellido())
                    .orElse("N/A");
        }
        return "N/A";
    }

    public String getEjemplarCodigo(Integer ejemplarId) {
        if (ejemplarId != null && ejemplares != null) {
            return ejemplares.stream()
                    .filter(e -> e.getId().equals(ejemplarId))
                    .findFirst()
                    .map(e -> "Ejemplar #" + e.getId())
                    .orElse("N/A");
        }
        return "N/A";
    }

    // ========== GETTERS Y SETTERS ==========

    public List<PrestamoDTO> getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(List<PrestamoDTO> prestamos) {
        this.prestamos = prestamos;
    }

    public PrestamoDTO getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(PrestamoDTO prestamo) {
        this.prestamo = prestamo;
    }

    public PrestamoDTO getPrestamoSeleccionado() {
        return prestamoSeleccionado;
    }

    public void setPrestamoSeleccionado(PrestamoDTO prestamoSeleccionado) {
        this.prestamoSeleccionado = prestamoSeleccionado;
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public List<UsuarioDTO> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<UsuarioDTO> usuarios) {
        this.usuarios = usuarios;
    }

    public List<EjemplarDTO> getEjemplares() {
        return ejemplares;
    }

    public void setEjemplares(List<EjemplarDTO> ejemplares) {
        this.ejemplares = ejemplares;
    }
}

