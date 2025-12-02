package com.libreria.beans;

import com.libreria.client.RestClient;
import com.libreria.dto.HistorialDTO;
import com.libreria.dto.UsuarioDTO;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Managed Bean para gestión de Historial.
 */
@Named("historialBean")
@ViewScoped
public class HistorialBean implements Serializable {

    @Inject
    private RestClient restClient;

    private List<HistorialDTO> historiales;
    private HistorialDTO historial;
    private HistorialDTO historialSeleccionado;

    // Listas para selects
    private List<UsuarioDTO> usuarios;

    @PostConstruct
    public void init() {
        historial = new HistorialDTO();
        cargarHistoriales();
        cargarUsuarios();
    }

    // ========== OPERACIONES CRUD ==========

    public void cargarHistoriales() {
        historiales = restClient.getHistoriales();
    }

    public void cargarUsuarios() {
        usuarios = restClient.getUsuarios();
    }

    public void nuevoHistorial() {
        historial = new HistorialDTO();
    }

    public void guardarHistorial() {
        try {
            if (historial.getId() == null) {
                // Crear nuevo
                HistorialDTO creado = restClient.createHistorial(historial);
                if (creado != null) {
                    addMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Historial creado correctamente");
                    cargarHistoriales();
                    nuevoHistorial();
                } else {
                    addMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo crear el historial");
                }
            } else {
                // Actualizar existente
                HistorialDTO actualizado = restClient.updateHistorial(historial.getId(), historial);
                if (actualizado != null) {
                    addMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Historial actualizado correctamente");
                    cargarHistoriales();
                    nuevoHistorial();
                } else {
                    addMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo actualizar el historial");
                }
            }
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ocurrió un error: " + e.getMessage());
        }
    }

    public void editarHistorial(HistorialDTO historial) {
        this.historial = new HistorialDTO();
        this.historial.setId(historial.getId());
        this.historial.setUsuarioId(historial.getUsuarioId());
        this.historial.setAccion(historial.getAccion());
        this.historial.setTablaAfectada(historial.getTablaAfectada());
        this.historial.setRegistroId(historial.getRegistroId());
        this.historial.setDetalles(historial.getDetalles());
    }

    public void eliminarHistorial(HistorialDTO historial) {
        try {
            boolean eliminado = restClient.deleteHistorial(historial.getId());
            if (eliminado) {
                addMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Historial eliminado correctamente");
                cargarHistoriales();
            } else {
                addMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo eliminar el historial");
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

    public List<HistorialDTO> getHistoriales() {
        return historiales;
    }

    public void setHistoriales(List<HistorialDTO> historiales) {
        this.historiales = historiales;
    }

    public HistorialDTO getHistorial() {
        return historial;
    }

    public void setHistorial(HistorialDTO historial) {
        this.historial = historial;
    }

    public HistorialDTO getHistorialSeleccionado() {
        return historialSeleccionado;
    }

    public void setHistorialSeleccionado(HistorialDTO historialSeleccionado) {
        this.historialSeleccionado = historialSeleccionado;
    }

    public List<UsuarioDTO> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<UsuarioDTO> usuarios) {
        this.usuarios = usuarios;
    }
}

