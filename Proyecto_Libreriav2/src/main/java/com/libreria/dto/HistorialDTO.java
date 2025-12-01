package com.libreria.dto;

import com.libreria.model.Historial;
import com.libreria.model.Usuario; // Necesario para la conversión
import java.io.Serializable;
import java.util.Date;

/**
 * DTO para la entidad Historial.
 * Utiliza usuarioId en lugar de la entidad Usuario completa.
 */
public class HistorialDTO implements Serializable {

    private Integer id;
    private Integer usuarioId; // Clave foránea como ID
    private String accion;
    private String tablaAfectada;
    private Integer registroId;
    private String detalles;
    private Date createdAt;

    // Constructores
    public HistorialDTO() {}

    /**
     * Constructor de conversión: Transforma la Entidad Historial a DTO.
     */
    public HistorialDTO(Historial h) {
        this.id = h.getId();
        // Mapear la relación ManyToOne al ID
        if (h.getUsuario() != null) {
            this.usuarioId = h.getUsuario().getId();
        }
        this.accion = h.getAccion();
        this.tablaAfectada = h.getTablaAfectada();
        this.registroId = h.getRegistroId();
        this.detalles = h.getDetalles();
        this.createdAt = h.getCreatedAt();
    }

    /**
     * Convierte el DTO a Entidad Historial (usado para la entrada POST/PUT).
     * NOTA: El objeto Usuario (relacionado) se establecerá en la capa de Servicio/DAO
     * utilizando el `usuarioId`.
     */
    public Historial toEntity() {
        Historial h = new Historial();
        h.setId(this.id);
        // El objeto Usuario se setea después de buscarlo por usuarioId
        h.setAccion(this.accion);
        h.setTablaAfectada(this.tablaAfectada);
        h.setRegistroId(this.registroId);
        h.setDetalles(this.detalles);
        return h;
    }

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Integer usuarioId) { this.usuarioId = usuarioId; }
    public String getAccion() { return accion; }
    public void setAccion(String accion) { this.accion = accion; }
    public String getTablaAfectada() { return tablaAfectada; }
    public void setTablaAfectada(String tablaAfectada) { this.tablaAfectada = tablaAfectada; }
    public Integer getRegistroId() { return registroId; }
    public void setRegistroId(Integer registroId) { this.registroId = registroId; }
    public String getDetalles() { return detalles; }
    public void setDetalles(String detalles) { this.detalles = detalles; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}