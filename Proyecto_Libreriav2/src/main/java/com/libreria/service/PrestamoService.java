package com.libreria.service;

import com.libreria.dao.EjemplarDAO; // Necesario para la relación
import com.libreria.dao.PrestamoDAO;
import com.libreria.dao.UsuarioDAO; // Necesario para la relación
import com.libreria.model.Ejemplar;
import com.libreria.model.Prestamo;
import com.libreria.model.Usuario;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.util.List;

/**
 * Servicio para manejar operaciones CRUD de la entidad Prestamo.
 */
@RequestScoped // CDI Scope
public class PrestamoService {

    // 🎯 Inyección del DAO (EJB/CDI)
    @Inject
    private PrestamoDAO prestamoDao;

    // 🎯 Inyección de DAOs para gestionar relaciones
    @Inject
    private UsuarioDAO usuarioDao;
    @Inject
    private EjemplarDAO ejemplarDao;

    // Método auxiliar (NO REQUERIDO, pero lo necesitas si los otros DAOs no existen)
    // private Usuario findUsuarioById(Integer usuarioId) { ... }
    // private Ejemplar findEjemplarById(Integer ejemplarId) { ... }

    // --------------------------------------------------
    // OPERACIÓN: CREAR (INSERTAR)
    // --------------------------------------------------
    // Se añade manejo de IDs para las relaciones, similar a MultaService
    public Prestamo create(Prestamo p, Integer usuarioId, Integer ejemplarId) {
        // 1. Obtener entidades relacionadas (Managed Entities)
        Usuario usuario = usuarioDao.find(usuarioId);
        Ejemplar ejemplar = ejemplarDao.find(ejemplarId);

        if (usuario == null) {
            throw new RuntimeException("Usuario con ID " + usuarioId + " no encontrado.");
        }
        if (ejemplar == null) {
            throw new RuntimeException("Ejemplar con ID " + ejemplarId + " no encontrado.");
        }

        // 2. Asignar relaciones
        p.setUsuario(usuario);
        p.setEjemplar(ejemplar);

        // 3. Persistir
        return prestamoDao.create(p);
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR POR ID (SELECT)
    // --------------------------------------------------
    public Prestamo find(Integer id) {
        return prestamoDao.find(id);
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR TODOS (SELECT ALL)
    // --------------------------------------------------
    public List<Prestamo> findAll() {
        return prestamoDao.findAll();
    }

    // --------------------------------------------------
    // OPERACIÓN: ACTUALIZAR (UPDATE)
    // --------------------------------------------------
    // Se añade manejo de IDs para las relaciones, similar a MultaService
    public Prestamo update(Integer id, Prestamo cambios, Integer usuarioId, Integer ejemplarId) {
        Prestamo p = prestamoDao.find(id);

        if (p == null) {
            return null;
        }

        // Aplicar cambios directos
        p.setFechaPrestamo(cambios.getFechaPrestamo());
        p.setFechaDevolucionPrevista(cambios.getFechaDevolucionPrevista());
        p.setFechaDevolucionReal(cambios.getFechaDevolucionReal());
        p.setEstado(cambios.getEstado());
        p.setMulta(cambios.getMulta());
        p.setObservaciones(cambios.getObservaciones());

        // Actualizar relación Usuario
        if (usuarioId != null && (p.getUsuario() == null || !p.getUsuario().getId().equals(usuarioId))) {
            Usuario usuario = usuarioDao.find(usuarioId);
            if (usuario == null) throw new RuntimeException("Usuario con ID " + usuarioId + " no encontrado.");
            p.setUsuario(usuario);
        }

        // Actualizar relación Ejemplar
        if (ejemplarId != null && (p.getEjemplar() == null || !p.getEjemplar().getId().equals(ejemplarId))) {
            Ejemplar ejemplar = ejemplarDao.find(ejemplarId);
            if (ejemplar == null) throw new RuntimeException("Ejemplar con ID " + ejemplarId + " no encontrado.");
            p.setEjemplar(ejemplar);
        }

        return prestamoDao.update(p);
    }

    // --------------------------------------------------
    // OPERACIÓN: ELIMINAR (DELETE)
    // --------------------------------------------------
    public boolean delete(Integer id) {
        return prestamoDao.delete(id);
    }
}