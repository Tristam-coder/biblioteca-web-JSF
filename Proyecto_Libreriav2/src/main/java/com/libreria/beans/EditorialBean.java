package com.libreria.beans;

import com.libreria.client.RestClient;
import com.libreria.dto.EditorialDTO;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Managed Bean para gestión de Editoriales.
 */
@Named("editorialBean")
@ViewScoped
public class EditorialBean implements Serializable {

    @Inject
    private RestClient restClient;

    private List<EditorialDTO> editoriales;
    private EditorialDTO editorial;
    private EditorialDTO editorialSeleccionada;

    @PostConstruct
    public void init() {
        editorial = new EditorialDTO();
        cargarEditoriales();
    }

    // ========== OPERACIONES CRUD ==========

    public void cargarEditoriales() {
        editoriales = restClient.getEditoriales();
    }

    public void nuevaEditorial() {
        editorial = new EditorialDTO();
    }

    public void guardarEditorial() {
        try {
            if (editorial.getId() == null) {
                // Crear nuevo
                EditorialDTO creada = restClient.createEditorial(editorial);
                if (creada != null) {
                    addMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Editorial creada correctamente");
                    cargarEditoriales();
                    nuevaEditorial();
                } else {
                    addMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo crear la editorial");
                }
            } else {
                // Actualizar existente
                EditorialDTO actualizada = restClient.updateEditorial(editorial.getId(), editorial);
                if (actualizada != null) {
                    addMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Editorial actualizada correctamente");
                    cargarEditoriales();
                    nuevaEditorial();
                } else {
                    addMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo actualizar la editorial");
                }
            }
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ocurrió un error: " + e.getMessage());
        }
    }

    public void editarEditorial(EditorialDTO editorial) {
        this.editorial = new EditorialDTO();
        this.editorial.setId(editorial.getId());
        this.editorial.setNombre(editorial.getNombre());
        this.editorial.setDireccion(editorial.getDireccion());
        this.editorial.setTelefono(editorial.getTelefono());
        this.editorial.setEmail(editorial.getEmail());
        this.editorial.setFechaFundacion(editorial.getFechaFundacion());
        this.editorial.setPais(editorial.getPais());
    }

    public void eliminarEditorial(EditorialDTO editorial) {
        try {
            boolean eliminada = restClient.deleteEditorial(editorial.getId());
            if (eliminada) {
                addMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Editorial eliminada correctamente");
                cargarEditoriales();
            } else {
                addMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo eliminar la editorial");
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

    public List<EditorialDTO> getEditoriales() {
        return editoriales;
    }

    public void setEditoriales(List<EditorialDTO> editoriales) {
        this.editoriales = editoriales;
    }

    public EditorialDTO getEditorial() {
        return editorial;
    }

    public void setEditorial(EditorialDTO editorial) {
        this.editorial = editorial;
    }

    public EditorialDTO getEditorialSeleccionada() {
        return editorialSeleccionada;
    }

    public void setEditorialSeleccionada(EditorialDTO editorialSeleccionada) {
        this.editorialSeleccionada = editorialSeleccionada;
    }
}

