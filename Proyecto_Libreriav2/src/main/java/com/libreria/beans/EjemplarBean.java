package com.libreria.beans;

import com.libreria.client.RestClient;
import com.libreria.dto.EjemplarDTO;
import com.libreria.dto.ObraDTO;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Managed Bean para gestión de Ejemplares.
 */
@Named("ejemplarBean")
@ViewScoped
public class EjemplarBean implements Serializable {

    @Inject
    private RestClient restClient;

    private List<EjemplarDTO> ejemplares;
    private EjemplarDTO ejemplar;
    private EjemplarDTO ejemplarSeleccionado;

    // Listas para selects
    private List<ObraDTO> obras;

    @PostConstruct
    public void init() {
        ejemplar = new EjemplarDTO();
        ejemplar.setEstado("DISPONIBLE");
        cargarEjemplares();
        cargarObras();
    }

    // ========== OPERACIONES CRUD ==========

    public void cargarEjemplares() {
        ejemplares = restClient.getEjemplares();
    }

    public void cargarObras() {
        obras = restClient.getObras();
    }

    public void nuevoEjemplar() {
        ejemplar = new EjemplarDTO();
        ejemplar.setEstado("DISPONIBLE");
    }

    public void guardarEjemplar() {
        try {
            if (ejemplar.getId() == null) {
                // Crear nuevo
                EjemplarDTO creado = restClient.createEjemplar(ejemplar);
                if (creado != null) {
                    addMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Ejemplar creado correctamente");
                    cargarEjemplares();
                    nuevoEjemplar();
                } else {
                    addMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo crear el ejemplar");
                }
            } else {
                // Actualizar existente
                EjemplarDTO actualizado = restClient.updateEjemplar(ejemplar.getId(), ejemplar);
                if (actualizado != null) {
                    addMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Ejemplar actualizado correctamente");
                    cargarEjemplares();
                    nuevoEjemplar();
                } else {
                    addMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo actualizar el ejemplar");
                }
            }
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ocurrió un error: " + e.getMessage());
        }
    }

    public void editarEjemplar(EjemplarDTO ejemplar) {
        this.ejemplar = new EjemplarDTO();
        this.ejemplar.setId(ejemplar.getId());
        this.ejemplar.setObraId(ejemplar.getObraId());
        this.ejemplar.setCodigoBarras(ejemplar.getCodigoBarras());
        this.ejemplar.setEstado(ejemplar.getEstado());
        this.ejemplar.setUbicacion(ejemplar.getUbicacion());
        this.ejemplar.setObservaciones(ejemplar.getObservaciones());
    }

    public void eliminarEjemplar(EjemplarDTO ejemplar) {
        try {
            boolean eliminado = restClient.deleteEjemplar(ejemplar.getId());
            if (eliminado) {
                addMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Ejemplar eliminado correctamente");
                cargarEjemplares();
            } else {
                addMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo eliminar el ejemplar");
            }
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ocurrió un error: " + e.getMessage());
        }
    }

    // ========== UTILIDADES ==========

    private void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
    }

    // ========== GETTERS Y SETTERS ==========

    public List<EjemplarDTO> getEjemplares() {
        return ejemplares;
    }

    public void setEjemplares(List<EjemplarDTO> ejemplares) {
        this.ejemplares = ejemplares;
    }

    public EjemplarDTO getEjemplar() {
        return ejemplar;
    }

    public void setEjemplar(EjemplarDTO ejemplar) {
        this.ejemplar = ejemplar;
    }

    public EjemplarDTO getEjemplarSeleccionado() {
        return ejemplarSeleccionado;
    }

    public void setEjemplarSeleccionado(EjemplarDTO ejemplarSeleccionado) {
        this.ejemplarSeleccionado = ejemplarSeleccionado;
    }

    public List<ObraDTO> getObras() {
        return obras;
    }

    public void setObras(List<ObraDTO> obras) {
        this.obras = obras;
    }
}

