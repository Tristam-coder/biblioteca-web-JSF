package com.libreria.service;

import com.libreria.model.Editorial;
import com.libreria.dao.EditorialDAO; // Importamos el DAO
import java.util.List;

/**
 * Servicio para manejar operaciones CRUD de la entidad Editorial.
 */
public class EditorialService {

    private final EditorialDAO dao = new EditorialDAO();

    // --------------------------------------------------
    // OPERACIÓN: CREAR (INSERTAR)
    // --------------------------------------------------
    public Editorial create(Editorial e) {
        // Lógica de negocio va aquí
        return dao.create(e);
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR POR ID (SELECT)
    // --------------------------------------------------
    public Editorial find(Integer id) {
        return dao.find(id);
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR TODOS (SELECT ALL)
    // --------------------------------------------------
    public List<Editorial> findAll() {
        return dao.findAll();
    }

    // --------------------------------------------------
    // OPERACIÓN: ACTUALIZAR (UPDATE)
    // --------------------------------------------------
    public Editorial update(Integer id, Editorial cambios) {

        // 1. Obtener la entidad gestionada por ID
        Editorial e = dao.find(id);

        if (e == null) {
            return null; // No encontrado
        }

        // 2. Aplicar cambios
        e.setNombre(cambios.getNombre());
        e.setDireccion(cambios.getDireccion());
        e.setTelefono(cambios.getTelefono());
        e.setEmail(cambios.getEmail());
        e.setFechaFundacion(cambios.getFechaFundacion());
        e.setPais(cambios.getPais());

        // 3. Persistir cambios usando el DAO
        return dao.update(e);
    }

    // --------------------------------------------------
    // OPERACIÓN: ELIMINAR (DELETE)
    // --------------------------------------------------
    public boolean delete(Integer id) {
        return dao.delete(id);
    }
}