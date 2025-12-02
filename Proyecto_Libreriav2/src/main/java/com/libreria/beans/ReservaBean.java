package com.libreria.beans;

import com.libreria.client.RestClient;
import com.libreria.dto.ReservaDTO;
import com.libreria.dto.UsuarioDTO;
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
 * Managed Bean para gestión de Reservas.
 */
@Named("reservaBean")
@ViewScoped
public class ReservaBean implements Serializable {

    @Inject
    private RestClient restClient;

    private List<ReservaDTO> reservas;
    private ReservaDTO reserva;
    private ReservaDTO reservaSeleccionada;

    // Listas para selects
    private List<UsuarioDTO> usuarios;
    private List<ObraDTO> obras;

    @PostConstruct
    public void init() {
        reserva = new ReservaDTO();
        reserva.setEstado("ACTIVA");
        cargarReservas();
        cargarUsuarios();
        cargarObras();
    }

    // ========== OPERACIONES CRUD ==========

    public void cargarReservas() {
        reservas = restClient.getReservas();
    }

    public void cargarUsuarios() {
        usuarios = restClient.getUsuarios();
    }

    public void cargarObras() {
        obras = restClient.getObras();
    }

    public void nuevaReserva() {
        reserva = new ReservaDTO();
        reserva.setEstado("ACTIVA");
    }

    public void guardarReserva() {
        try {
            if (reserva.getId() == null) {
                // Crear nuevo
                ReservaDTO creada = restClient.createReserva(reserva);
                if (creada != null) {
                    addMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Reserva creada correctamente");
                    cargarReservas();
                    nuevaReserva();
                } else {
                    addMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo crear la reserva");
                }
            } else {
                // Actualizar existente
                ReservaDTO actualizada = restClient.updateReserva(reserva.getId(), reserva);
                if (actualizada != null) {
                    addMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Reserva actualizada correctamente");
                    cargarReservas();
                    nuevaReserva();
                } else {
                    addMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo actualizar la reserva");
                }
            }
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ocurrió un error: " + e.getMessage());
        }
    }

    public void editarReserva(ReservaDTO reserva) {
        this.reserva = new ReservaDTO();
        this.reserva.setId(reserva.getId());
        this.reserva.setUsuarioId(reserva.getUsuarioId());
        this.reserva.setObraId(reserva.getObraId());
        this.reserva.setFechaReserva(reserva.getFechaReserva());
        this.reserva.setEstado(reserva.getEstado());
    }

    public void eliminarReserva(ReservaDTO reserva) {
        try {
            boolean eliminada = restClient.deleteReserva(reserva.getId());
            if (eliminada) {
                addMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Reserva eliminada correctamente");
                cargarReservas();
            } else {
                addMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo eliminar la reserva");
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

    public List<ReservaDTO> getReservas() {
        return reservas;
    }

    public void setReservas(List<ReservaDTO> reservas) {
        this.reservas = reservas;
    }

    public ReservaDTO getReserva() {
        return reserva;
    }

    public void setReserva(ReservaDTO reserva) {
        this.reserva = reserva;
    }

    public ReservaDTO getReservaSeleccionada() {
        return reservaSeleccionada;
    }

    public void setReservaSeleccionada(ReservaDTO reservaSeleccionada) {
        this.reservaSeleccionada = reservaSeleccionada;
    }

    public List<UsuarioDTO> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<UsuarioDTO> usuarios) {
        this.usuarios = usuarios;
    }

    public List<ObraDTO> getObras() {
        return obras;
    }

    public void setObras(List<ObraDTO> obras) {
        this.obras = obras;
    }
}


