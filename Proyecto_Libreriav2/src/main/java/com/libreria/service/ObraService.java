package com.libreria.service;

import com.libreria.dao.EditorialDAO; // Necesario para la relación
import com.libreria.dao.ObraDAO;
import com.libreria.dao.TipoObraDAO; // Necesario para la relación
import com.libreria.model.Editorial;
import com.libreria.model.Obra;
import com.libreria.model.TipoObra;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.util.List;

/**
 * Servicio para manejar operaciones CRUD de la entidad Obra.
 */
@RequestScoped // CDI Scope
public class ObraService {

    @Inject
    private ObraDAO obraDao;

    // 🎯 Asumimos que existen los DAOs para las relaciones:
    @Inject
    private TipoObraDAO tipoObraDao;
    @Inject
    private EditorialDAO editorialDao;


    // --------------------------------------------------
    // OPERACIÓN: CREAR (INSERTAR)
    // --------------------------------------------------
    public Obra create(Obra o, Integer tipoObraId, Integer editorialId) {

        // 1. Obtener entidades relacionadas (Managed Entities)
        TipoObra tipoObra = tipoObraDao.find(tipoObraId);
        Editorial editorial = editorialDao.find(editorialId);

        if (tipoObra == null) {
            throw new RuntimeException("Tipo de Obra con ID " + tipoObraId + " no encontrado.");
        }
        if (editorial == null) {
            throw new RuntimeException("Editorial con ID " + editorialId + " no encontrada.");
        }

        // 2. Asignar relaciones
        o.setTipoObra(tipoObra);
        o.setEditorial(editorial);

        // 3. Persistir
        return obraDao.create(o);
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR POR ID (SELECT)
    // --------------------------------------------------
    public Obra find(Integer id) {
        return obraDao.find(id);
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR TODOS (SELECT ALL)
    // --------------------------------------------------
    public List<Obra> findAll() {
        return obraDao.findAll();
    }

    // --------------------------------------------------
    // OPERACIÓN: ACTUALIZAR (UPDATE)
    // --------------------------------------------------
    public Obra update(Integer id, Obra cambios, Integer tipoObraId, Integer editorialId) {
        Obra o = obraDao.find(id);

        if (o == null) {
            return null;
        }

        // Aplicar cambios de campos directos:
        o.setTitulo(cambios.getTitulo());
        o.setIsbnIssn(cambios.getIsbnIssn());
        o.setAnioPublicacion(cambios.getAnioPublicacion());
        o.setEdicion(cambios.getEdicion());
        o.setNumeroPaginas(cambios.getNumeroPaginas());
        o.setIdioma(cambios.getIdioma());
        o.setAreaTematica(cambios.getAreaTematica());
        o.setNivel(cambios.getNivel());
        o.setDescripcion(cambios.getDescripcion());
        o.setEstado(cambios.getEstado());

        // 1. Actualizar relación TipoObra
        if (tipoObraId != null && (o.getTipoObra() == null || !o.getTipoObra().getId().equals(tipoObraId))) {
            TipoObra tipoObra = tipoObraDao.find(tipoObraId);
            if (tipoObra == null) throw new RuntimeException("Tipo Obra ID " + tipoObraId + " no encontrado.");
            o.setTipoObra(tipoObra);
        }

        // 2. Actualizar relación Editorial
        if (editorialId != null && (o.getEditorial() == null || !o.getEditorial().getId().equals(editorialId))) {
            Editorial editorial = editorialDao.find(editorialId);
            if (editorial == null) throw new RuntimeException("Editorial ID " + editorialId + " no encontrada.");
            o.setEditorial(editorial);
        }

        return obraDao.update(o);
    }

    // --------------------------------------------------
    // OPERACIÓN: ELIMINAR (DELETE)
    // --------------------------------------------------
    public boolean delete(Integer id) {
        return obraDao.delete(id);
    }
}