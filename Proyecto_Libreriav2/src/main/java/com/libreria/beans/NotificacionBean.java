package com.libreria.beans;

import com.libreria.client.RestClient;
import com.libreria.dto.NotificacionDTO;
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
 * Managed Bean para gestión de Notificaciones.
 */
@Named("notificacionBean")
@ViewScoped
public class NotificacionBean implements Serializable {

    @Inject
    private RestClient restClient;

    private List<NotificacionDTO> notificaciones;
    private NotificacionDTO notificacion;
    private NotificacionDTO notificacionSeleccionada;

    // Listas para selects
    private List<UsuarioDTO> usuarios;

    @PostConstruct
    public void init() {
        notificacion = new NotificacionDTO();
        notificacion.setLeida(false);
        cargarNotificaciones();
        cargarUsuarios();
    }

    // ========== OPERACIONES CRUD ==========

    public void cargarNotificaciones() {
        notificaciones = restClient.getNotificaciones();
    }

    public void cargarUsuarios() {
        usuarios = restClient.getUsuarios();
    }

    public void nuevaNotificacion() {
        notificacion = new NotificacionDTO();
        notificacion.setLeida(false);
    }

    public void guardarNotificacion() {
        try {
            if (notificacion.getId() == null) {
                // Crear nuevo
                NotificacionDTO creada = restClient.createNotificacion(notificacion);
                if (creada != null) {
                    addMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Notificación creada correctamente");
                    cargarNotificaciones();
                    nuevaNotificacion();
                } else {
                    addMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo crear la notificación");
                }
            } else {
                // Actualizar existente
                NotificacionDTO actualizada = restClient.updateNotificacion(notificacion.getId(), notificacion);
                if (actualizada != null) {
                    addMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Notificación actualizada correctamente");
                    cargarNotificaciones();
                    nuevaNotificacion();
                } else {
                    addMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo actualizar la notificación");
                }
            }
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ocurrió un error: " + e.getMessage());
        }
    }

    public void editarNotificacion(NotificacionDTO notificacion) {
        this.notificacion = new NotificacionDTO();
        this.notificacion.setId(notificacion.getId());
        this.notificacion.setUsuarioId(notificacion.getUsuarioId());
        this.notificacion.setTipo(notificacion.getTipo());
        this.notificacion.setMensaje(notificacion.getMensaje());
        this.notificacion.setLeida(notificacion.getLeida());
        this.notificacion.setFechaEnvio(notificacion.getFechaEnvio());
    }

    public void eliminarNotificacion(NotificacionDTO notificacion) {
        try {
            boolean eliminada = restClient.deleteNotificacion(notificacion.getId());
            if (eliminada) {
                addMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Notificación eliminada correctamente");
                cargarNotificaciones();
            } else {
                addMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo eliminar la notificación");
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

    public List<NotificacionDTO> getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(List<NotificacionDTO> notificaciones) {
        this.notificaciones = notificaciones;
    }

    public NotificacionDTO getNotificacion() {
        return notificacion;
    }

    public void setNotificacion(NotificacionDTO notificacion) {
        this.notificacion = notificacion;
    }

    public NotificacionDTO getNotificacionSeleccionada() {
        return notificacionSeleccionada;
    }

    public void setNotificacionSeleccionada(NotificacionDTO notificacionSeleccionada) {
        this.notificacionSeleccionada = notificacionSeleccionada;
    }

    public List<UsuarioDTO> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<UsuarioDTO> usuarios) {
        this.usuarios = usuarios;
    }
}

