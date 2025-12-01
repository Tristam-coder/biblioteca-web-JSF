/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.libreria.model;

/**
 *
 * @author CESAR
 */

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Modelo para la tabla tipo_obra
 */
@Entity
@Table(name = "tipo_obra")
public class TipoObra implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Lob
    private String descripcion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    // Relación OneToMany con Obra
    @OneToMany(mappedBy = "tipoObra")
    private Set<Obra> obras;

    // Constructores, Getters y Setters
    public TipoObra() {
        this.createdAt = new Date();
    }
    // ... Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Set<Obra> getObras() {
        return obras;
    }

    public void setObras(Set<Obra> obras) {
        this.obras = obras;
    }
}