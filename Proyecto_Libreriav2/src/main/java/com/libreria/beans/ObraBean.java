package com.libreria.beans;

import com.libreria.client.RestClient;
import com.libreria.dto.ObraDTO;
import com.libreria.dto.TipoObraDTO;
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
 * Managed Bean para gestión de Obras.
 */
@Named("obraBean")
@ViewScoped
public class ObraBean implements Serializable {

    @Inject
    private RestClient restClient;

    private List<ObraDTO> obras;
    private ObraDTO obra;
    private ObraDTO obraSeleccionada;
    private String filtro;

    // Listas para selects
    private List<TipoObraDTO> tiposObra;
    private List<EditorialDTO> editoriales;

    @PostConstruct
    public void init() {
        obra = new ObraDTO();
        obra.setEstado("DISPONIBLE");
        cargarObras();
        cargarTiposObra();
        cargarEditoriales();
    }

    // ========== OPERACIONES CRUD ==========

    public void cargarObras() {
        obras = restClient.getObras();
    }

    public void cargarTiposObra() {
        tiposObra = restClient.getTiposObra();
    }

    public void cargarEditoriales() {
        editoriales = restClient.getEditoriales();
    }

    public void nuevaObra() {
        obra = new ObraDTO();
        obra.setEstado("DISPONIBLE");
    }

    public void guardarObra() {
        try {
            if (obra.getId() == null) {
                // Crear nuevo
                ObraDTO creada = restClient.createObra(obra);
                if (creada != null) {
                    addMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Obra creada correctamente");
                    cargarObras();
                    nuevaObra();
                } else {
                    addMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo crear la obra");
                }
            } else {
                // Actualizar existente
                ObraDTO actualizada = restClient.updateObra(obra.getId(), obra);
                if (actualizada != null) {
                    addMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Obra actualizada correctamente");
                    cargarObras();
                    nuevaObra();
                } else {
                    addMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo actualizar la obra");
                }
            }
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ocurrió un error: " + e.getMessage());
        }
    }

    public void editarObra(ObraDTO obra) {
        this.obra = new ObraDTO();
        this.obra.setId(obra.getId());
        this.obra.setTitulo(obra.getTitulo());
        this.obra.setIsbnIssn(obra.getIsbnIssn());
        this.obra.setTipoObraId(obra.getTipoObraId());
        this.obra.setEditorialId(obra.getEditorialId());
        this.obra.setAnioPublicacion(obra.getAnioPublicacion());
        this.obra.setEdicion(obra.getEdicion());
        this.obra.setNumeroPaginas(obra.getNumeroPaginas());
        this.obra.setIdioma(obra.getIdioma());
        this.obra.setAreaTematica(obra.getAreaTematica());
        this.obra.setNivel(obra.getNivel());
        this.obra.setDescripcion(obra.getDescripcion());
        this.obra.setEstado(obra.getEstado());
    }

    public void eliminarObra(Integer id) {
        try {
            boolean eliminada = restClient.deleteObra(id);
            if (eliminada) {
                addMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Obra eliminada correctamente");
                cargarObras();
            } else {
                addMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo eliminar la obra");
            }
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ocurrió un error: " + e.getMessage());
        }
    }

    public void seleccionarObra(ObraDTO obra) {
        this.obraSeleccionada = obra;
    }

    // ========== UTILIDADES ==========

    private void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(severity, summary, detail));
    }

    public List<ObraDTO> getObrasFiltradas() {
        if (filtro == null || filtro.isEmpty()) {
            return obras;
        }
        return obras.stream()
                .filter(o -> o.getTitulo().toLowerCase().contains(filtro.toLowerCase()) ||
                            (o.getIsbnIssn() != null && o.getIsbnIssn().toLowerCase().contains(filtro.toLowerCase())))
                .toList();
    }

    public String getTipoObraNombre(Integer tipoObraId) {
        if (tipoObraId != null && tiposObra != null) {
            return tiposObra.stream()
                    .filter(t -> t.getId().equals(tipoObraId))
                    .findFirst()
                    .map(TipoObraDTO::getNombre)
                    .orElse("N/A");
        }
        return "N/A";
    }

    public String getEditorialNombre(Integer editorialId) {
        if (editorialId != null && editoriales != null) {
            return editoriales.stream()
                    .filter(e -> e.getId().equals(editorialId))
                    .findFirst()
                    .map(EditorialDTO::getNombre)
                    .orElse("N/A");
        }
        return "N/A";
    }

    // ========== GETTERS Y SETTERS ==========

    public List<ObraDTO> getObras() {
        return obras;
    }

    public void setObras(List<ObraDTO> obras) {
        this.obras = obras;
    }

    public ObraDTO getObra() {
        return obra;
    }

    public void setObra(ObraDTO obra) {
        this.obra = obra;
    }

    public ObraDTO getObraSeleccionada() {
        return obraSeleccionada;
    }

    public void setObraSeleccionada(ObraDTO obraSeleccionada) {
        this.obraSeleccionada = obraSeleccionada;
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public List<TipoObraDTO> getTiposObra() {
        return tiposObra;
    }

    public void setTiposObra(List<TipoObraDTO> tiposObra) {
        this.tiposObra = tiposObra;
    }

    public List<EditorialDTO> getEditoriales() {
        return editoriales;
    }

    public void setEditoriales(List<EditorialDTO> editoriales) {
        this.editoriales = editoriales;
    }
}

