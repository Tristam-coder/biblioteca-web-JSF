package com.libreria.dto;

import com.libreria.model.Notificacion;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class NotificacionDTO {

    private Integer id;
    private Integer usuarioId; // Solo el ID de la relación
    private String tipo;
    private String mensaje;
    private Boolean leida;
    private LocalDateTime fechaEnvio;
    private LocalDateTime createdAt;

    // --- Constructor vacío ---
    public NotificacionDTO() {
    }

    // --- Constructor para mapear Entidad a DTO ---
    public NotificacionDTO(Notificacion notificacion) {
        this.id = notificacion.getId();
        this.tipo = notificacion.getTipo();
        this.mensaje = notificacion.getMensaje();
        this.leida = notificacion.getLeida();

        // Convertir java.util.Date a java.time.LocalDateTime para modernizar la API
        this.fechaEnvio = convertDateToLocalDateTime(notificacion.getFechaEnvio());
        this.createdAt = convertDateToLocalDateTime(notificacion.getCreatedAt());

        if (notificacion.getUsuario() != null) {
            this.usuarioId = notificacion.getUsuario().getId();
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
    public Notificacion toEntity() {
        Notificacion n = new Notificacion();
        n.setId(this.id);
        n.setTipo(this.tipo);
        n.setMensaje(this.mensaje);
        n.setLeida(this.leida);
        n.setFechaEnvio(convertLocalDateTimeToDate(this.fechaEnvio));
        // createdAt se inicializa en el constructor de la entidad
        // La entidad Usuario se configura en el Service
        return n;
    }

    // --- Getters y Setters ---

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Integer usuarioId) { this.usuarioId = usuarioId; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public Boolean getLeida() { return leida; }
    public void setLeida(Boolean leida) { this.leida = leida; }

    public LocalDateTime getFechaEnvio() { return fechaEnvio; }
    public void setFechaEnvio(LocalDateTime fechaEnvio) { this.fechaEnvio = fechaEnvio; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}