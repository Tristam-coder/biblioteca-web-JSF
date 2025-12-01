package com.libreria.dto;

import com.libreria.model.Ejemplar;
import com.libreria.model.Obra; // Necesario para la conversión
import java.io.Serializable;
import java.util.Date;

/**
 * DTO para la entidad Ejemplar.
 * Utiliza obraId en lugar de la entidad Obra completa.
 */
public class EjemplarDTO implements Serializable {

    private Integer id;
    private Integer obraId; // Clave foránea como ID
    private String codigoBarras;
    private String estado;
    private String ubicacion;
    private String observaciones;
    private Date createdAt;

    // Constructores
    public EjemplarDTO() {}

    /**
     * Constructor de conversión: Transforma la Entidad Ejemplar a DTO.
     */
    public EjemplarDTO(Ejemplar e) {
        this.id = e.getId();
        // Mapear la relación ManyToOne al ID
        if (e.getObra() != null) {
            this.obraId = e.getObra().getId();
        }
        this.codigoBarras = e.getCodigoBarras();
        this.estado = e.getEstado();
        this.ubicacion = e.getUbicacion();
        this.observaciones = e.getObservaciones();
        this.createdAt = e.getCreatedAt();
    }

    /**
     * Convierte el DTO a Entidad Ejemplar (usado para la entrada POST/PUT).
     * NOTA: La entidad Obra (relacionada) debe ser establecida en la capa de Servicio/DAO
     * usando el `obraId`. Aquí solo creamos el esqueleto.
     */
    public Ejemplar toEntity() {
        Ejemplar e = new Ejemplar();
        e.setId(this.id);
        // La Obra (objeto completo) se setea después de buscarla por ObraId
        e.setCodigoBarras(this.codigoBarras);
        e.setEstado(this.estado);
        e.setUbicacion(this.ubicacion);
        e.setObservaciones(this.observaciones);
        return e;
    }

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getObraId() { return obraId; }
    public void setObraId(Integer obraId) { this.obraId = obraId; }
    public String getCodigoBarras() { return codigoBarras; }
    public void setCodigoBarras(String codigoBarras) { this.codigoBarras = codigoBarras; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}