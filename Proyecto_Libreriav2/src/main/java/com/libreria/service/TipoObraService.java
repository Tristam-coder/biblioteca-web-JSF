package com.libreria.service;

import com.libreria.dao.TipoObraDAO;
import com.libreria.model.TipoObra;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.util.List;

/**
 * Servicio para manejar operaciones CRUD de la entidad TipoObra.
 */
@RequestScoped // CDI Scope
public class TipoObraService {

    @Inject // Inyección del DAO
    private TipoObraDAO tipoObraDao;

    // --------------------------------------------------
    // OPERACIÓN: CREAR (INSERTAR)
    // --------------------------------------------------
    // No necesita IDs de relaciones
    public TipoObra create(TipoObra t) {
        return tipoObraDao.create(t);
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR POR ID (SELECT)
    // --------------------------------------------------
    public TipoObra find(Integer id) {
        return tipoObraDao.find(id);
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR TODOS (SELECT ALL)
    // --------------------------------------------------
    public List<TipoObra> findAll() {
        return tipoObraDao.findAll();
    }

    // --------------------------------------------------
    // OPERACIÓN: ACTUALIZAR (UPDATE)
    // --------------------------------------------------
    // No necesita IDs de relaciones
    public TipoObra update(Integer id, TipoObra cambios) {
        TipoObra t = tipoObraDao.find(id);

        if (t == null) {
            return null;
        }

        // Aplicar cambios:
        t.setNombre(cambios.getNombre());
        t.setDescripcion(cambios.getDescripcion());

        return tipoObraDao.update(t);
    }

    // --------------------------------------------------
    // OPERACIÓN: ELIMINAR (DELETE)
    // --------------------------------------------------
    public boolean delete(Integer id) {
        return tipoObraDao.delete(id);
    }
}