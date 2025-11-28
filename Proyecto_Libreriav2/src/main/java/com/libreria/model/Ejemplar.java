/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.libreria.model;


import jakarta.persistence.*;
import jakarta.json.bind.annotation.JsonbTransient; // ¡IMPORTANTE! Importamos la anotación para excluir de la serialización JSON
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Modelo para la tabla ejemplar
 */
@Entity
@Table(name = "ejemplar")
public class Ejemplar implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Columna obra_id (Clave Foránea)
    // Las relaciones @ManyToOne suelen ser EAGER y no necesitan JsonbTransient, 
    // pero si Obra tiene relaciones inversas (OneToMany) que apuntan a Ejemplar, 
    // puede ser necesario añadir JsonbTransient en Obra para evitar ciclos.
    @ManyToOne
    @JoinColumn(name = "obra_id", referencedColumnName = "id", nullable = true)
    private Obra obra;

    @Column(name = "codigo_barras", nullable = false, length = 100, unique = true)
    private String codigoBarras;

    @Column(length = 50)
    private String estado = "DISPONIBLE";

    @Column(length = 200)
    private String ubicacion;

    @Column(length = 200)
    private String observaciones;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    // Relación OneToMany con Prestamo - Marcada como JsonbTransient para evitar LazyInitializationException
    @OneToMany(mappedBy = "ejemplar")
    @JsonbTransient // <-- Aplicamos la exclusión aquí
    private Set<Prestamo> prestamos;

    // Constructores, Getters y Setters
    public Ejemplar() {
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

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
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

    public Set<Prestamo> getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(Set<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }
}