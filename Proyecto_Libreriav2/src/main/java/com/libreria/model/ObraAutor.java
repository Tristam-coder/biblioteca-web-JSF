/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.libreria.model;


import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Modelo para la tabla obra_autor (Relación Muchos a Muchos)
 */
@Entity
@Table(name = "obra_autor", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"obra_id", "autor_id"})
})
public class ObraAutor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Columna obra_id (Clave Foránea)
    @ManyToOne
    @JoinColumn(name = "obra_id", referencedColumnName = "id", nullable = true)
    private Obra obra;

    // Columna autor_id (Clave Foránea)
    @ManyToOne
    @JoinColumn(name = "autor_id", referencedColumnName = "id", nullable = true)
    private Autor autor;

    @Column(name = "tipo_contribucion", length = 100)
    private String tipoContribucion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    // Constructores, Getters y Setters
    public ObraAutor() {
        this.createdAt = new Date();
    }
    // ... Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Obra getObra() {
        return obra;
    }

    public void setObra(Obra obra) {
        this.obra = obra;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getTipoContribucion() {
        return tipoContribucion;
    }

    public void setTipoContribucion(String tipoContribucion) {
        this.tipoContribucion = tipoContribucion;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}