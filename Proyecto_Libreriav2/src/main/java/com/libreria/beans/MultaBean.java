package com.libreria.beans;

import com.libreria.client.RestClient;
import com.libreria.dto.MultaDTO;
import com.libreria.dto.UsuarioDTO;
import com.libreria.dto.PrestamoDTO;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Managed Bean para gestión de Multas.
 */
@Named("multaBean")
@ViewScoped
public class MultaBean implements Serializable {

    @Inject
    private RestClient restClient;

    private List<MultaDTO> multas;
    private MultaDTO multa;
    private MultaDTO multaSeleccionada;

    // Listas para selects
    private List<UsuarioDTO> usuarios;
    private List<PrestamoDTO> prestamos;

    @PostConstruct
    public void init() {
        multa = new MultaDTO();
        multa.setEstado("PENDIENTE");
        cargarMultas();
        cargarUsuarios();
        cargarPrestamos();
    }

    // ========== OPERACIONES CRUD ==========

    public void cargarMultas() {
        multas = restClient.getMultas();
    }

    public void cargarUsuarios() {
        usuarios = restClient.getUsuarios();
    }

    public void cargarPrestamos() {
        prestamos = restClient.getPrestamos();
    }

    public void nuevaMulta() {
        multa = new MultaDTO();
        multa.setEstado("PENDIENTE");
    }

    public void guardarMulta() {
        try {
            if (multa.getId() == null) {
                // Crear nuevo
                MultaDTO creada = restClient.createMulta(multa);
                if (creada != null) {
                    addMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Multa creada correctamente");
                    cargarMultas();
                    nuevaMulta();
                } else {
                    addMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo crear la multa");
                }
            } else {
                // Actualizar existente
                MultaDTO actualizada = restClient.updateMulta(multa.getId(), multa);
                if (actualizada != null) {
                    addMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Multa actualizada correctamente");
                    cargarMultas();
                    nuevaMulta();
                } else {
                    addMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo actualizar la multa");
                }
            }
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ocurrió un error: " + e.getMessage());
        }
    }

    public void editarMulta(MultaDTO multa) {
        this.multa = new MultaDTO();
        this.multa.setId(multa.getId());
        this.multa.setUsuarioId(multa.getUsuarioId());
        this.multa.setPrestamoId(multa.getPrestamoId());
        this.multa.setMonto(multa.getMonto());
        this.multa.setFechaGeneracion(multa.getFechaGeneracion());
        this.multa.setMotivo(multa.getMotivo());
        this.multa.setEstado(multa.getEstado());
        this.multa.setFechaPago(multa.getFechaPago());
    }

    public void eliminarMulta(MultaDTO multa) {
        try {
            boolean eliminada = restClient.deleteMulta(multa.getId());
            if (eliminada) {
                addMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Multa eliminada correctamente");
                cargarMultas();
            } else {
                addMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo eliminar la multa");
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

    public List<MultaDTO> getMultas() {
        return multas;
    }

    public void setMultas(List<MultaDTO> multas) {
        this.multas = multas;
    }

    public MultaDTO getMulta() {
        return multa;
    }

    public void setMulta(MultaDTO multa) {
        this.multa = multa;
    }

    public MultaDTO getMultaSeleccionada() {
        return multaSeleccionada;
    }

    public void setMultaSeleccionada(MultaDTO multaSeleccionada) {
        this.multaSeleccionada = multaSeleccionada;
    }

    public List<UsuarioDTO> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<UsuarioDTO> usuarios) {
        this.usuarios = usuarios;
    }

    public List<PrestamoDTO> getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(List<PrestamoDTO> prestamos) {
        this.prestamos = prestamos;
    }
}

