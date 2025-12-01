package com.libreria.service;

import com.libreria.dao.AutorDAO;   // Necesario para la relación
import com.libreria.dao.ObraAutorDAO;
import com.libreria.dao.ObraDAO;    // Necesario para la relación
import com.libreria.model.Autor;
import com.libreria.model.Obra;
import com.libreria.model.ObraAutor;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.util.List;

/**
 * Servicio para manejar operaciones CRUD de la entidad ObraAutor.
 */
@RequestScoped
public class ObraAutorService {

    @Inject
    private ObraAutorDAO obraAutorDao;

    // 🎯 Asumimos que existen los DAOs para las entidades relacionadas:
    @Inject
    private ObraDAO obraDao;
    @Inject
    private AutorDAO autorDao;

    // --------------------------------------------------
    // OPERACIÓN: CREAR (INSERTAR)
    // --------------------------------------------------
    public ObraAutor create(ObraAutor oa, Integer obraId, Integer autorId) {

        // 1. Obtener entidades relacionadas (Managed Entities)
        Obra obra = obraDao.find(obraId);
        Autor autor = autorDao.find(autorId);

        if (obra == null) {
            throw new RuntimeException("Obra con ID " + obraId + " no encontrada.");
        }
        if (autor == null) {
            throw new RuntimeException("Autor con ID " + autorId + " no encontrado.");
        }

        // 2. Asignar relaciones
        oa.setObra(obra);
        oa.setAutor(autor);

        // 3. Persistir
        return obraAutorDao.create(oa);
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR POR ID (SELECT)
    // --------------------------------------------------
    public ObraAutor find(Integer id) {
        return obraAutorDao.find(id);
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR TODOS (SELECT ALL)
    // --------------------------------------------------
    public List<ObraAutor> findAll() {
        return obraAutorDao.findAll();
    }

    // --------------------------------------------------
    // OPERACIÓN: ACTUALIZAR (UPDATE)
    // --------------------------------------------------
    public ObraAutor update(Integer id, ObraAutor cambios, Integer obraId, Integer autorId) {
        ObraAutor oa = obraAutorDao.find(id);

        if (oa == null) {
            return null;
        }

        // Aplicar cambios:
        oa.setTipoContribucion(cambios.getTipoContribucion());

        // 1. Actualizar relación Obra
        if (obraId != null && (oa.getObra() == null || !oa.getObra().getId().equals(obraId))) {
            Obra obra = obraDao.find(obraId);
            if (obra == null) throw new RuntimeException("Obra ID " + obraId + " no encontrada.");
            oa.setObra(obra);
        }

        // 2. Actualizar relación Autor
        if (autorId != null && (oa.getAutor() == null || !oa.getAutor().getId().equals(autorId))) {
            Autor autor = autorDao.find(autorId);
            if (autor == null) throw new RuntimeException("Autor ID " + autorId + " no encontrado.");
            oa.setAutor(autor);
        }

        return obraAutorDao.update(oa);
    }

    // --------------------------------------------------
    // OPERACIÓN: ELIMINAR (DELETE)
    // --------------------------------------------------
    public boolean delete(Integer id) {
        return obraAutorDao.delete(id);
    }
}