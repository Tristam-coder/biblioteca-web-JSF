package com.libreria.dto;

import com.libreria.model.Obra;
import java.io.Serializable;

public class ObraDTO implements Serializable {

    private Integer id;
    private String titulo;
    private String isbnIssn;
    private Integer tipoObraId; // Solo el ID de la relación
    private Integer editorialId; // Solo el ID de la relación
    private Integer anioPublicacion;
    private Integer edicion;
    private Integer numeroPaginas;
    private String idioma;
    private String areaTematica;
    private String nivel;
    private String descripcion;
    private String estado;
    // createdAt y las colecciones (ejemplares, autores, reservas) no se incluyen

    // --- Constructor vacío ---
    public ObraDTO() {
    }

    // --- Constructor para mapear Entidad a DTO ---
    public ObraDTO(Obra obra) {
        this.id = obra.getId();
        this.titulo = obra.getTitulo();
        this.isbnIssn = obra.getIsbnIssn();
        this.anioPublicacion = obra.getAnioPublicacion();
        this.edicion = obra.getEdicion();
        this.numeroPaginas = obra.getNumeroPaginas();
        this.idioma = obra.getIdioma();
        this.areaTematica = obra.getAreaTematica();
        this.nivel = obra.getNivel();
        this.descripcion = obra.getDescripcion();
        this.estado = obra.getEstado();

        // Mapear IDs de relaciones
        if (obra.getTipoObra() != null) {
            this.tipoObraId = obra.getTipoObra().getId();
        }
        if (obra.getEditorial() != null) {
            this.editorialId = obra.getEditorial().getId();
        }
    }

    // --- Mapear DTO a Entidad (Solo datos directos, las relaciones se setean en el Service) ---
    public Obra toEntity() {
        Obra o = new Obra();
        o.setId(this.id);
        o.setTitulo(this.titulo);
        o.setIsbnIssn(this.isbnIssn);
        o.setAnioPublicacion(this.anioPublicacion);
        o.setEdicion(this.edicion);
        o.setNumeroPaginas(this.numeroPaginas);
        o.setIdioma(this.idioma);
        o.setAreaTematica(this.areaTematica);
        o.setNivel(this.nivel);
        o.setDescripcion(this.descripcion);
        o.setEstado(this.estado);
        // Las entidades TipoObra y Editorial se configuran en el Service
        return o;
    }

    // --- Getters y Setters ---

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getIsbnIssn() { return isbnIssn; }
    public void setIsbnIssn(String isbnIssn) { this.isbnIssn = isbnIssn; }

    public Integer getTipoObraId() { return tipoObraId; }
    public void setTipoObraId(Integer tipoObraId) { this.tipoObraId = tipoObraId; }

    public Integer getEditorialId() { return editorialId; }
    public void setEditorialId(Integer editorialId) { this.editorialId = editorialId; }

    public Integer getAnioPublicacion() { return anioPublicacion; }
    public void setAnioPublicacion(Integer anioPublicacion) { this.anioPublicacion = anioPublicacion; }

    public Integer getEdicion() { return edicion; }
    public void setEdicion(Integer edicion) { this.edicion = edicion; }

    public Integer getNumeroPaginas() { return numeroPaginas; }
    public void setNumeroPaginas(Integer numeroPaginas) { this.numeroPaginas = numeroPaginas; }

    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }

    public String getAreaTematica() { return areaTematica; }
    public void setAreaTematica(String areaTematica) { this.areaTematica = areaTematica; }

    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}