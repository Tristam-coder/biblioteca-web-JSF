package com.libreria.dto;

import com.libreria.model.Usuario; // Para la conversión
import java.io.Serializable;
import java.util.Date;

/**
 * Data Transfer Object para la entidad Usuario.
 * Excluye la contraseña y las relaciones para una transferencia segura y ligera.
 */
public class UsuarioDTO implements Serializable {

    // Campos expuestos:
    private Integer id;
    private String dni;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String direccion;
    private String tipoUsuario;
    private String estado;
    private Date fechaRegistro;
    private Date createdAt;

    // Constructores y Getters/Setters...

    public UsuarioDTO() {}

    // Constructor de conversión (útil para crear el DTO a partir de la Entidad)
    public UsuarioDTO(Usuario u) {
        this.id = u.getId();
        this.dni = u.getDni();
        this.nombre = u.getNombre();
        this.apellido = u.getApellido();
        this.email = u.getEmail();
        this.telefono = u.getTelefono();
        this.direccion = u.getDireccion();
        this.tipoUsuario = u.getTipoUsuario();
        this.estado = u.getEstado();
        this.fechaRegistro = u.getFechaRegistro();
        this.createdAt = u.getCreatedAt();
    }

    // Convertir DTO a Entidad (útil para POST/PUT, no incluye campos solo de lectura)
    public Usuario toEntity() {
        Usuario u = new Usuario();
        u.setId(this.id);
        u.setDni(this.dni);
        u.setNombre(this.nombre);
        u.setApellido(this.apellido);
        u.setEmail(this.email);
        u.setTelefono(this.telefono);
        u.setDireccion(this.direccion);
        u.setTipoUsuario(this.tipoUsuario);
        u.setEstado(this.estado);
        u.setFechaRegistro(this.fechaRegistro);
        // NO incluimos createdAt/password/relaciones, son manejadas por el Service/DB/DAO
        return u;
    }

    // Getters y Setters...
    // ... (Añadir los getters y setters aquí)
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    // ... y el resto de getters/setters para los campos expuestos
    // ...

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}