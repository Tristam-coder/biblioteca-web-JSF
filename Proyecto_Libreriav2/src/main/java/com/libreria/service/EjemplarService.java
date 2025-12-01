package com.libreria.service;

import com.libreria.dao.EjemplarDAO;
import com.libreria.model.Ejemplar;
import com.libreria.model.Obra; // Necesario para gestionar la relación
import java.util.List;

/**
 * Servicio para manejar operaciones CRUD de la entidad Ejemplar.
 */
public class EjemplarService {

    private final EjemplarDAO dao = new EjemplarDAO();
    // NOTA: Asumimos la existencia de ObraService o ObraDAO para buscar la Obra relacionada.
    // Usaremos un placeholder ObraService para demostrar la lógica.
    // private final ObraService obraService = new ObraService(); 

    // Helper para simular la búsqueda de Obra (debería existir en tu capa de servicio/DAO)
    private Obra findObraById(Integer obraId) {
        // En un caso real: return obraService.find(obraId);
        // Placeholder:
        if (obraId != null) {
            // En un entorno JPA, usarías em.getReference(Obra.class, obraId) o em.find()
            Obra obra = new Obra();
            obra.setId(obraId);
            return obra;
        }
        return null;
    }

    // --------------------------------------------------
    // OPERACIÓN: CREAR (INSERTAR)
    // --------------------------------------------------
    public Ejemplar create(Ejemplar e, Integer obraId) {
        // Lógica de negocio: establecer la Obra a partir del ID
        Obra obra = findObraById(obraId);
        if (obra == null && obraId != null) {
            throw new RuntimeException("Obra con ID " + obraId + " no encontrada.");
        }
        e.setObra(obra);

        return dao.create(e);
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR POR ID (SELECT)
    // --------------------------------------------------
    public Ejemplar find(Integer id) {
        return dao.find(id);
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR TODOS (SELECT ALL)
    // --------------------------------------------------
    public List<Ejemplar> findAll() {
        return dao.findAll();
    }

    // --------------------------------------------------
    // OPERACIÓN: ACTUALIZAR (UPDATE)
    // --------------------------------------------------
    public Ejemplar update(Integer id, Ejemplar cambios, Integer obraId) {

        Ejemplar e = dao.find(id);

        if (e == null) {
            return null;
        }

        // 1. Aplicar cambios a la entidad existente
        e.setCodigoBarras(cambios.getCodigoBarras());
        e.setEstado(cambios.getEstado());
        e.setUbicacion(cambios.getUbicacion());
        e.setObservaciones(cambios.getObservaciones());

        // 2. Actualizar la relación Obra si el ID fue proporcionado
        if (obraId != null) {
            Obra obra = findObraById(obraId);
            if (obra == null) {
                throw new RuntimeException("Obra con ID " + obraId + " no encontrada para la actualización.");
            }
            e.setObra(obra);
        }

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