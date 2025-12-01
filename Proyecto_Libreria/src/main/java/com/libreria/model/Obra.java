/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.libreria.model;


import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Modelo para la tabla obra
 */
@Entity
@Table(name = "obra")
public class Obra implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 500)
    private String titulo;

    @Column(name = "isbn_issn", length = 20, unique = true)
    private String isbnIssn;

    // Columna tipo_obra_id (Clave Foránea)
    @ManyToOne
    @JoinColumn(name = "tipo_obra_id", referencedColumnName = "id", nullable = true)
    private TipoObra tipoObra;

    // Columna editorial_id (Clave Foránea)
    @ManyToOne
    @JoinColumn(name = "editorial_id", referencedColumnName = "id", nullable = true)
    private Editorial editorial;

    @Column(name = "anio_publicacion")
    private Integer anioPublicacion;

    private Integer edicion;

    @Column(name = "numero_paginas")
    private Integer numeroPaginas;

    @Column(length = 100)
    private String idioma;

    @Column(name = "area_tematica", length = 200)
    private String areaTematica;

    @Column(length = 50)
    private String nivel;

    @Lob
    private String descripcion;

    @Column(length = 50)
    private String estado = "DISPONIBLE";

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    // Relaciones OneToMany
    @OneToMany(mappedBy = "obra")
    private Set<Ejemplar> ejemplares;

    @OneToMany(mappedBy = "obra")
    private Set<ObraAutor> autores;

    @OneToMany(mappedBy = "obra")
    private Set<Reserva> reservas;

    // Constructores, Getters y Setters
    public Obra() {
        this.createdAt = new Date();
    }
    // ... Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIsbnIssn() {
        return isbnIssn;
    }

    public void setIsbnIssn(String isbnIssn) {
        this.isbnIssn = isbnIssn;
    }

    public TipoObra getTipoObra() {
        return tipoObra;
    }

    public void setTipoObra(TipoObra tipoObra) {
        this.tipoObra = tipoObra;
    }

    public Editorial getEditorial() {
        return editorial;
    }

    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
    }

    public Integer getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(Integer anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    public Integer getEdicion() {
        return edicion;
    }

    public void setEdicion(Integer edicion) {
        this.edicion = edicion;
    }

    public Integer getNumeroPaginas() {
        return numeroPaginas;
    }

    public void setNumeroPaginas(Integer numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getAreaTematica() {
        return areaTematica;
    }

    public void setAreaTematica(String areaTematica) {
        this.areaTematica = areaTematica;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Set<Ejemplar> getEjemplares() {
        return ejemplares;
    }

    public void setEjemplares(Set<Ejemplar> ejemplares) {
        this.ejemplares = ejemplares;
    }

    public Set<ObraAutor> getAutores() {
        return autores;
    }

    public void setAutores(Set<ObraAutor> autores) {
        this.autores = autores;
    }

    public Set<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(Set<Reserva> reservas) {
        this.reservas = reservas;
    }
}