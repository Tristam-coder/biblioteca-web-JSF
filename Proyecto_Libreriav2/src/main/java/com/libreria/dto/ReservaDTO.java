package com.libreria.dto;

import com.libreria.model.Reserva;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class ReservaDTO implements Serializable {

    private Integer id;
    private Integer usuarioId; // ID del Usuario
    private Integer obraId;    // ID de la Obra
    private LocalDateTime fechaReserva;
    private LocalDateTime fechaDisponibilidadEstimada;
    private String estado;
    private Integer posicionCola;
    private Boolean notificado;

    // --- Constructor vacío ---
    public ReservaDTO() {
    }

    // --- Constructor para mapear Entidad a DTO ---
    public ReservaDTO(Reserva reserva) {
        this.id = reserva.getId();
        this.estado = reserva.getEstado();
        this.posicionCola = reserva.getPosicionCola();
        this.notificado = reserva.getNotificado();

        // Conversión de Date a LocalDateTime para modernizar la API
        this.fechaReserva = convertDateToLocalDateTime(reserva.getFechaReserva());
        this.fechaDisponibilidadEstimada = convertDateToLocalDateTime(reserva.getFechaDisponibilidadEstimada());

        // Mapear IDs de relaciones
        if (reserva.getUsuario() != null) {
            this.usuarioId = reserva.getUsuario().getId();
        }
        if (reserva.getObra() != null) {
            this.obraId = reserva.getObra().getId();
        }
    }

    // Método auxiliar para conversión
    private LocalDateTime convertDateToLocalDateTime(Date date) {
        if (date == null) return null;
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    // Método auxiliar para conversión
    private Date convertLocalDateTimeToDate(LocalDateTime localDateTime) {
        if (localDateTime == null) return null;
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }


    // --- Mapear DTO a Entidad (Sin relaciones) ---
    public Reserva toEntity() {
        Reserva r = new Reserva();
        r.setId(this.id);
        r.setEstado(this.estado);
        r.setPosicionCola(this.posicionCola);
        r.setNotificado(this.notificado);

        r.setFechaReserva(convertLocalDateTimeToDate(this.fechaReserva));
        r.setFechaDisponibilidadEstimada(convertLocalDateTimeToDate(this.fechaDisponibilidadEstimada));

        // Las entidades Usuario y Obra se configuran en el Service
        return r;
    }

    // --- Getters y Setters ---

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Integer usuarioId) { this.usuarioId = usuarioId; }

    public Integer getObraId() { return obraId; }
    public void setObraId(Integer obraId) { this.obraId = obraId; }

    public LocalDateTime getFechaReserva() { return fechaReserva; }
    public void setFechaReserva(LocalDateTime fechaReserva) { this.fechaReserva = fechaReserva; }

    public LocalDateTime getFechaDisponibilidadEstimada() { return fechaDisponibilidadEstimada; }
    public void setFechaDisponibilidadEstimada(LocalDateTime fechaDisponibilidadEstimada) { this.fechaDisponibilidadEstimada = fechaDisponibilidadEstimada; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Integer getPosicionCola() { return posicionCola; }
    public void setPosicionCola(Integer posicionCola) { this.posicionCola = posicionCola; }

    public Boolean getNotificado() { return notificado; }
    public void setNotificado(Boolean notificado) { this.notificado = notificado; }
}