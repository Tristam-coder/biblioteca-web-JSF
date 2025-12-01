/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.libreria.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Modelo para la tabla reserva
 */
@Entity
@Table(name = "reserva")
public class Reserva implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Columna usuario_id (Clave Foránea)
    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = true)
    private Usuario usuario;

    // Columna obra_id (Clave Foránea)
    @ManyToOne
    @JoinColumn(name = "obra_id", referencedColumnName = "id", nullable = true)
    private Obra obra;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_reserva")
    private Date fechaReserva;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_disponibilidad_estimada")
    private Date fechaDisponibilidadEstimada;

    @Column(length = 50)
    private String estado = "PENDIENTE";

    @Column(name = "posicion_cola")
    private Integer posicionCola;

    private Boolean notificado = false;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    // Constructores, Getters y Setters
    public Reserva() {
        this.createdAt = new Date();
        this.fechaReserva = new Date();
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

    public Obra getObra() {
        return obra;
    }

    public void setObra(Obra obra) {
        this.obra = obra;
    }

    public Date getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(Date fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public Date getFechaDisponibilidadEstimada() {
        return fechaDisponibilidadEstimada;
    }

    public void setFechaDisponibilidadEstimada(Date fechaDisponibilidadEstimada) {
        this.fechaDisponibilidadEstimada = fechaDisponibilidadEstimada;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getPosicionCola() {
        return posicionCola;
    }

    public void setPosicionCola(Integer posicionCola) {
        this.posicionCola = posicionCola;
    }

    public Boolean getNotificado() {
        return notificado;
    }

    public void setNotificado(Boolean notificado) {
        this.notificado = notificado;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}