/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.libreria.model;

import jakarta.persistence.*;
import jakarta.json.bind.annotation.JsonbTransient; // Importación para excluir de la serialización JSON
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Modelo para la tabla editorial
 */
@Entity
@Table(name = "editorial")
public class Editorial implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 255)
    private String nombre;

    @Column(name = "direccion")
    private String direccion; // Una dirección es siempre un String.

    @Column(length = 20)
    private String telefono;

    @Column(length = 255)
    private String email;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_fundacion")
    private Date fechaFundacion;

    @Column(length = 100)
    private String pais;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    // Relación OneToMany con Obra - Marcada como JsonbTransient para evitar LazyInitializationException
    // Ya que por defecto se cargan de forma perezosa (LAZY) y el serializador JSON falla al acceder a ellas.
    @OneToMany(mappedBy = "editorial")
    @JsonbTransient
    private Set<Obra> obrasPublicadas;

    // Constructores, Getters y Setters
    public Editorial() {
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getFechaFundacion() {
        return fechaFundacion;
    }

    public void setFechaFundacion(Date fechaFundacion) {
        this.fechaFundacion = fechaFundacion;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Set<Obra> getObrasPublicadas() {
        return obrasPublicadas;
    }

    public void setObrasPublicadas(Set<Obra> obrasPublicadas) {
        this.obrasPublicadas = obrasPublicadas;
    }
}