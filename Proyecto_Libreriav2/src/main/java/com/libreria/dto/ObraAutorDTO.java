package com.libreria.dto;

import com.libreria.model.ObraAutor;
import java.io.Serializable;

public class ObraAutorDTO implements Serializable {

    private Integer id;
    private Integer obraId;     // ID de la Obra relacionada
    private Integer autorId;    // ID del Autor relacionado
    private String tipoContribucion;

    // --- Constructor vacío ---
    public ObraAutorDTO() {
    }

    // --- Constructor para mapear Entidad a DTO ---
    public ObraAutorDTO(ObraAutor obraAutor) {
        this.id = obraAutor.getId();
        this.tipoContribucion = obraAutor.getTipoContribucion();

        // Mapear IDs de relaciones
        if (obraAutor.getObra() != null) {
            this.obraId = obraAutor.getObra().getId();
        }
        if (obraAutor.getAutor() != null) {
            this.autorId = obraAutor.getAutor().getId();
        }
    }

    // --- Mapear DTO a Entidad (Sin relaciones) ---
    public ObraAutor toEntity() {
        ObraAutor oa = new ObraAutor();
        oa.setId(this.id);
        oa.setTipoContribucion(this.tipoContribucion);
        // Las entidades Obra y Autor se configuran en el Service
        return oa;
    }

    // --- Getters y Setters ---

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getObraId() { return obraId; }
    public void setObraId(Integer obraId) { this.obraId = obraId; }

    public Integer getAutorId() { return autorId; }
    public void setAutorId(Integer autorId) { this.autorId = autorId; }

    public String getTipoContribucion() { return tipoContribucion; }
    public void setTipoContribucion(String tipoContribucion) { this.tipoContribucion = tipoContribucion; }
}