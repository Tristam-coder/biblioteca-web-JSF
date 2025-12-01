package com.libreria.service;

import com.libreria.model.Autor;
import com.libreria.dao.AutorDAO; // Importamos el DAO
import java.util.List;

/**
 * Servicio para manejar operaciones CRUD de la entidad Autor (Lógica de Negocio).
 */
public class AutorService {

    // Instanciamos el DAO
    private final AutorDAO dao = new AutorDAO();

    // --------------------------------------------------
    // OPERACIÓN: CREAR (INSERTAR)
    // --------------------------------------------------
    public Autor create(Autor a) {
        // Lógica de negocio (ej. validaciones) iría aquí
        return dao.create(a);
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR POR ID (SELECT)
    // --------------------------------------------------
    public Autor find(Integer id) {
        return dao.find(id);
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR TODOS (SELECT ALL)
    // --------------------------------------------------
    public List<Autor> findAll() {
        return dao.findAll();
    }

    // --------------------------------------------------
    // OPERACIÓN: ACTUALIZAR (UPDATE)
    // --------------------------------------------------
    public Autor update(Integer id, Autor cambios) {

        // 1. Encontrar la entidad existente
        Autor a = dao.find(id);

        if (a == null) {
            return null;
        }

        // 2. Aplicar cambios a la entidad existente 'a'
        // NOTA: Se deben añadir comprobaciones de nulo si 'cambios' solo contiene un subconjunto de campos
        a.setNombre(cambios.getNombre());
        a.setApellido(cambios.getApellido());
        a.setFechaNacimiento(cambios.getFechaNacimiento());
        a.setNacionalidad(cambios.getNacionalidad());
        a.setBiografia(cambios.getBiografia());

        // 3. Persistir los cambios usando el DAO
        return dao.update(a);
    }

    // --------------------------------------------------
    // OPERACIÓN: ELIMINAR (DELETE)
    // --------------------------------------------------
    public boolean delete(Integer id) {
        return dao.delete(id);
    }
}