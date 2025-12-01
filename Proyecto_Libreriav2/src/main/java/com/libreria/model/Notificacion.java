/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.libreria.model;

import jakarta.persistence.*;
import jakarta.json.bind.annotation.JsonbTransient; // ¡IMPORTANTE! Importación para exclusión JSON
import java.io.Serializable;
import java.util.Date;

/**
 * Modelo para la tabla notificacion
 */
@Entity
@Table(name = "notificacion")
public class Notificacion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Columna usuario_id (Clave Foránea)
    // Se marca como JsonbTransient para evitar la serialización del objeto Usuario
    // y prevenir posibles ciclos de referencia.
    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = true)
    @JsonbTransient
    private Usuario usuario;

    @Column(nullable = false, length = 100)
    private String tipo;

    @Lob
    @Column(nullable = false)
    private String mensaje;

    @Column
    private Boolean leida = false;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_envio")
    private Date fechaEnvio;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    // Constructores, Getters y Setters
    public Notificacion() {
        this.createdAt = new Date();
        this.fechaEnvio = new Date();
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Boolean getLeida() {
        return leida;
    }

    public void setLeida(Boolean leida) {
        this.leida = leida;
    }

    public Date getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(Date fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}