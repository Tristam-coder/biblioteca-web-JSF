/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.libreria.model;



import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * Modelo para la tabla prestamo
 */
@Entity
@Table(name = "prestamo")
public class Prestamo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Columna usuario_id (Clave Foránea)
    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = true)
    private Usuario usuario;

    // Columna ejemplar_id (Clave Foránea)
    @ManyToOne
    @JoinColumn(name = "ejemplar_id", referencedColumnName = "id", nullable = true)
    private Ejemplar ejemplar;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_prestamo", nullable = false)
    private Date fechaPrestamo;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_devolucion_prevista", nullable = false)
    private Date fechaDevolucionPrevista;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_devolucion_real")
    private Date fechaDevolucionReal;

    @Column(length = 50)
    private String estado = "ACTIVO";

    @Column(precision = 10, scale = 2)
    private BigDecimal multa = BigDecimal.ZERO;

    @Lob
    private String observaciones;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    // Relación OneToMany con Multa
    @OneToMany(mappedBy = "prestamo")
    private Set<Multa> multasAsociadas;

    // Constructores, Getters y Setters
    public Prestamo() {
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

    public Ejemplar getEjemplar() {
        return ejemplar;
    }

    public void setEjemplar(Ejemplar ejemplar) {
        this.ejemplar = ejemplar;
    }

    public Date getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(Date fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public Date getFechaDevolucionPrevista() {
        return fechaDevolucionPrevista;
    }

    public void setFechaDevolucionPrevista(Date fechaDevolucionPrevista) {
        this.fechaDevolucionPrevista = fechaDevolucionPrevista;
    }

    public Date getFechaDevolucionReal() {
        return fechaDevolucionReal;
    }

    public void setFechaDevolucionReal(Date fechaDevolucionReal) {
        this.fechaDevolucionReal = fechaDevolucionReal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigDecimal getMulta() {
        return multa;
    }

    public void setMulta(BigDecimal multa) {
        this.multa = multa;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Set<Multa> getMultasAsociadas() {
        return multasAsociadas;
    }

    public void setMultasAsociadas(Set<Multa> multasAsociadas) {
        this.multasAsociadas = multasAsociadas;
    }
}