/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.libreria.model;

import jakarta.persistence.*;
import jakarta.json.bind.annotation.JsonbTransient; // Importación para exclusión JSON
import java.io.Serializable;
import java.util.Date;

/**
 * Modelo para la tabla historial
 */
@Entity
@Table(name = "historial")
public class Historial implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Columna usuario_id (Clave Foránea)
    // Se marca como JsonbTransient para evitar la serialización completa del objeto Usuario
    // y prevenir posibles ciclos de referencia al generar la respuesta JSON.
    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = true) // ON DELETE SET NULL
    @JsonbTransient 
    private Usuario usuario;

    @Column(nullable = false, length = 100)
    private String accion;

    @Column(name = "tabla_afectada", length = 100)
    private String tablaAfectada;

    @Column(name = "registro_id")
    private Integer registroId;

    @Column(length = 200)
    private String detalles;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    // Constructores, Getters y Setters
    public Historial() {
        this.createdAt = new Date();
    }

    // ... Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getTablaAfectada() {
        return tablaAfectada;
    }

    public void setTablaAfectada(String tablaAfectada) {
        this.tablaAfectada = tablaAfectada;
    }

    public Integer getRegistroId() {
        return registroId;
    }

    public void setRegistroId(Integer registroId) {
        this.registroId = registroId;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}