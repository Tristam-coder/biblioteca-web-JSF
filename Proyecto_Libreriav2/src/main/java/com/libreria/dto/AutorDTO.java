package com.libreria.dto;

import com.libreria.model.Autor;
import java.io.Serializable;
import java.util.Date;

/**
 * DTO para la entidad Autor.
 * Usado para transferir datos de forma ligera y segura.
 */
public class AutorDTO implements Serializable {

    private Integer id;
    private String nombre;
    private String apellido;
    private Date fechaNacimiento;
    private String nacionalidad;
    private String biografia;
    private Date createdAt; // Incluido para lectura/información

    // Constructores
    public AutorDTO() {}

    /**
     * Constructor de conversión: Transforma la Entidad Autor a DTO.
     */
    public AutorDTO(Autor a) {
        this.id = a.getId();
        this.nombre = a.getNombre();
        this.apellido = a.getApellido();
        this.fechaNacimiento = a.getFechaNacimiento();
        this.nacionalidad = a.getNacionalidad();
        this.biografia = a.getBiografia();
        this.createdAt = a.getCreatedAt();
    }

    /**
     * Convierte el DTO a Entidad Autor (usado para POST/PUT).
     */
    public Autor toEntity() {
        Autor a = new Autor();
        a.setId(this.id);
        a.setNombre(this.nombre);
        a.setApellido(this.apellido);
        a.setFechaNacimiento(this.fechaNacimiento);
        a.setNacionalidad(this.nacionalidad);
        a.setBiografia(this.biografia);
        // createdAt y obras no se establecen desde el DTO de entrada
        return a;
    }

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public Date getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(Date fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public String getNacionalidad() { return nacionalidad; }
    public void setNacionalidad(String nacionalidad) { this.nacionalidad = nacionalidad; }
    public String getBiografia() { return biografia; }
    public void setBiografia(String biografia) { this.biografia = biografia; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}