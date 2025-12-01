package com.libreria.dto;

import com.libreria.model.TipoObra;
import java.io.Serializable;

public class TipoObraDTO implements Serializable {

    private Integer id;
    private String nombre;
    private String descripcion;
    // createdAt y la colección 'obras' no se incluyen

    // --- Constructor vacío ---
    public TipoObraDTO() {
    }

    // --- Constructor para mapear Entidad a DTO ---
    public TipoObraDTO(TipoObra tipoObra) {
        this.id = tipoObra.getId();
        this.nombre = tipoObra.getNombre();
        this.descripcion = tipoObra.getDescripcion();
    }

    // --- Mapear DTO a Entidad ---
    public TipoObra toEntity() {
        TipoObra t = new TipoObra();
        t.setId(this.id);
        t.setNombre(this.nombre);
        t.setDescripcion(this.descripcion);
        return t;
    }

    // --- Getters y Setters ---

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}