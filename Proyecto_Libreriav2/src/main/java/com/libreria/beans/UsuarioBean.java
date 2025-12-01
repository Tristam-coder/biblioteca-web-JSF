package com.libreria.beans;

import com.libreria.client.RestClient;
import com.libreria.dto.UsuarioDTO;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Managed Bean para gestión de Usuarios.
 * Consume la API REST para operaciones CRUD.
 */
@Named("usuarioBean")
@ViewScoped
public class UsuarioBean implements Serializable {

    @Inject
    private RestClient restClient;

    private List<UsuarioDTO> usuarios;
    private UsuarioDTO usuario;
    private UsuarioDTO usuarioSeleccionado;
    private String filtro;

    @PostConstruct
    public void init() {
        usuario = new UsuarioDTO();
        usuario.setTipoUsuario("SOCIO");
        usuario.setEstado("ACTIVO");

        cargarUsuarios();
    }

    // ========== OPERACIONES CRUD ==========

    public void cargarUsuarios() {
        usuarios = restClient.getUsuarios();
    }

    public void nuevoUsuario() {
        usuario = new UsuarioDTO();
        usuario.setTipoUsuario("SOCIO");
        usuario.setEstado("ACTIVO");
        // usuario.setFechaRegistro(LocalDate.now()); // TODO: El DTO usa Date, actualizar después
    }

    public void guardarUsuario() {
        try {
            if (usuario.getId() == null) {
                // Crear nuevo
                UsuarioDTO creado = restClient.createUsuario(usuario);
                if (creado != null) {
                    addMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Usuario creado correctamente");
                    cargarUsuarios();
                    nuevoUsuario();
                } else {
                    addMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo crear el usuario");
                }
            } else {
                // Actualizar existente
                UsuarioDTO actualizado = restClient.updateUsuario(usuario.getId(), usuario);
                if (actualizado != null) {
                    addMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Usuario actualizado correctamente");
                    cargarUsuarios();
                    nuevoUsuario();
                } else {
                    addMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo actualizar el usuario");
                }
            }
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ocurrió un error: " + e.getMessage());
        }
    }

    public void editarUsuario(UsuarioDTO usuario) {
        this.usuario = new UsuarioDTO();
        this.usuario.setId(usuario.getId());
        this.usuario.setDni(usuario.getDni());
        this.usuario.setNombre(usuario.getNombre());
        this.usuario.setApellido(usuario.getApellido());
        this.usuario.setEmail(usuario.getEmail());
        this.usuario.setTelefono(usuario.getTelefono());
        this.usuario.setDireccion(usuario.getDireccion());
        this.usuario.setTipoUsuario(usuario.getTipoUsuario());
        this.usuario.setEstado(usuario.getEstado());
        this.usuario.setFechaRegistro(usuario.getFechaRegistro());
    }

    public void eliminarUsuario(Integer id) {
        try {
            boolean eliminado = restClient.deleteUsuario(id);
            if (eliminado) {
                addMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Usuario eliminado correctamente");
                cargarUsuarios();
            } else {
                addMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo eliminar el usuario");
            }
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ocurrió un error: " + e.getMessage());
        }
    }

    public void seleccionarUsuario(UsuarioDTO usuario) {
        this.usuarioSeleccionado = usuario;
    }

    // ========== UTILIDADES ==========

    private void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(severity, summary, detail));
    }

    public List<UsuarioDTO> getUsuariosFiltrados() {
        if (filtro == null || filtro.isEmpty()) {
            return usuarios;
        }
        return usuarios.stream()
                .filter(u -> u.getNombre().toLowerCase().contains(filtro.toLowerCase()) ||
                            u.getApellido().toLowerCase().contains(filtro.toLowerCase()) ||
                            u.getEmail().toLowerCase().contains(filtro.toLowerCase()) ||
                            (u.getDni() != null && u.getDni().toLowerCase().contains(filtro.toLowerCase())))
                .toList();
    }

    // ========== GETTERS Y SETTERS ==========

    public List<UsuarioDTO> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<UsuarioDTO> usuarios) {
        this.usuarios = usuarios;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public UsuarioDTO getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    public void setUsuarioSeleccionado(UsuarioDTO usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }
}

