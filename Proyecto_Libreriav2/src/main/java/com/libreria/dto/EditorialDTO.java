package com.libreria.dto;

import com.libreria.model.Editorial;
import java.io.Serializable;
import java.util.Date;

/**
 * DTO para la entidad Editorial.
 */
public class EditorialDTO implements Serializable {

    private Integer id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String email;
    private Date fechaFundacion;
    private String pais;
    private Date createdAt;

    // Constructores
    public EditorialDTO() {}

    /**
     * Constructor de conversión: Entidad a DTO.
     */
    public EditorialDTO(Editorial e) {
        this.id = e.getId();
        this.nombre = e.getNombre();
        this.direccion = e.getDireccion();
        this.telefono = e.getTelefono();
        this.email = e.getEmail();
        this.fechaFundacion = e.getFechaFundacion();
        this.pais = e.getPais();
        this.createdAt = e.getCreatedAt();
    }

    /**
     * Convierte el DTO a Entidad (usado para la entrada POST/PUT).
     */
    public Editorial toEntity() {
        Editorial e = new Editorial();
        e.setId(this.id);
        e.setNombre(this.nombre);
        e.setDireccion(this.direccion);
        e.setTelefono(this.telefono);
        e.setEmail(this.email);
        e.setFechaFundacion(this.fechaFundacion);
        e.setPais(this.pais);
        // createdAt no se establece desde el DTO de entrada
        return e;
    }

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Date getFechaFundacion() { return fechaFundacion; }
    public void setFechaFundacion(Date fechaFundacion) { this.fechaFundacion = fechaFundacion; }
    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}