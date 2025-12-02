package com.libreria.beans;

import com.libreria.client.RestClient;
import com.libreria.dto.AutorDTO;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Managed Bean para gestión de Autores.
 */
@Named("autorBean")
@ViewScoped
public class AutorBean implements Serializable {

    @Inject
    private RestClient restClient;

    private List<AutorDTO> autores;
    private AutorDTO autor;
    private AutorDTO autorSeleccionado;

    @PostConstruct
    public void init() {
        autor = new AutorDTO();
        cargarAutores();
    }

    // ========== OPERACIONES CRUD ==========

    public void cargarAutores() {
        autores = restClient.getAutores();
    }

    public void nuevoAutor() {
        autor = new AutorDTO();
    }

    public void guardarAutor() {
        try {
            if (autor.getId() == null) {
                // Crear nuevo
                AutorDTO creado = restClient.createAutor(autor);
                if (creado != null) {
                    addMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Autor creado correctamente");
                    cargarAutores();
                    nuevoAutor();
                } else {
                    addMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo crear el autor");
                }
            } else {
                // Actualizar existente
                AutorDTO actualizado = restClient.updateAutor(autor.getId(), autor);
                if (actualizado != null) {
                    addMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Autor actualizado correctamente");
                    cargarAutores();
                    nuevoAutor();
                } else {
                    addMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo actualizar el autor");
                }
            }
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ocurrió un error: " + e.getMessage());
        }
    }

    public void editarAutor(AutorDTO autor) {
        this.autor = new AutorDTO();
        this.autor.setId(autor.getId());
        this.autor.setNombre(autor.getNombre());
        this.autor.setApellido(autor.getApellido());
        this.autor.setFechaNacimiento(autor.getFechaNacimiento());
        this.autor.setNacionalidad(autor.getNacionalidad());
        this.autor.setBiografia(autor.getBiografia());
    }

    public void eliminarAutor(AutorDTO autor) {
        try {
            boolean eliminado = restClient.deleteAutor(autor.getId());
            if (eliminado) {
                addMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Autor eliminado correctamente");
                cargarAutores();
            } else {
                addMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo eliminar el autor");
            }
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ocurrió un error: " + e.getMessage());
        }
    }

    // ========== UTILIDADES ==========

    private void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
    }

    // ========== GETTERS Y SETTERS ==========

    public List<AutorDTO> getAutores() {
        return autores;
    }

    public void setAutores(List<AutorDTO> autores) {
        this.autores = autores;
    }

    public AutorDTO getAutor() {
        return autor;
    }

    public void setAutor(AutorDTO autor) {
        this.autor = autor;
    }

    public AutorDTO getAutorSeleccionado() {
        return autorSeleccionado;
    }

    public void setAutorSeleccionado(AutorDTO autorSeleccionado) {
        this.autorSeleccionado = autorSeleccionado;
    }
}


