package com.libreria.service;

import com.libreria.dao.MultaDAO;
import com.libreria.dao.PrestamoDAO;
import com.libreria.dao.UsuarioDAO;
import com.libreria.model.Multa;
import com.libreria.model.Prestamo;
import com.libreria.model.Usuario;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.util.List;

/**
 * Servicio para manejar operaciones CRUD de la entidad Multa.
 */
@RequestScoped // Importante para la inyección (CDI)
public class MultaService {

    @Inject
    private MultaDAO multaDao;

    // 🎯 Asumimos que tienes otros DAOs inyectados para las relaciones
    @Inject
    private UsuarioDAO usuarioDao;
    @Inject
    private PrestamoDAO prestamoDao;

    // --------------------------------------------------
    // OPERACIÓN: CREAR (INSERTAR)
    // --------------------------------------------------
    public Multa create(Multa m, Integer usuarioId, Integer prestamoId) {
        // 1. Obtener las entidades relacionadas (deben ser Managed Entities)
        Usuario usuario = usuarioDao.find(usuarioId);
        Prestamo prestamo = (prestamoId != null) ? prestamoDao.find(prestamoId) : null;

        // **Lógica de validación:**
        if (usuario == null) {
            throw new RuntimeException("Usuario con ID " + usuarioId + " no encontrado.");
        }
        if (prestamo == null && prestamoId != null) {
            throw new RuntimeException("Préstamo con ID " + prestamoId + " no encontrado.");
        }

        // 2. Establecer relaciones en la entidad Multa
        m.setUsuario(usuario);
        m.setPrestamo(prestamo);

        // 3. Persistir
        return multaDao.create(m);
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR POR ID (SELECT)
    // --------------------------------------------------
    public Multa find(Integer id) {
        return multaDao.find(id);
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR TODOS (SELECT ALL)
    // --------------------------------------------------
    public List<Multa> findAll() {
        return multaDao.findAll();
    }

    // --------------------------------------------------
    // OPERACIÓN: ACTUALIZAR (UPDATE)
    // --------------------------------------------------
    public Multa update(Integer id, Multa cambios, Integer usuarioId, Integer prestamoId) {

        Multa m = multaDao.find(id);

        if (m == null) {
            return null;
        }

        // 1. Aplicar cambios a campos directos
        m.setMonto(cambios.getMonto());
        m.setMotivo(cambios.getMotivo());
        m.setEstado(cambios.getEstado());
        m.setFechaGeneracion(cambios.getFechaGeneracion());
        m.setFechaPago(cambios.getFechaPago());

        // 2. Actualizar la relación Usuario si el ID fue proporcionado
        if (usuarioId != null && (m.getUsuario() == null || !m.getUsuario().getId().equals(usuarioId))) {
            Usuario usuario = usuarioDao.find(usuarioId);
            if (usuario == null) {
                throw new RuntimeException("Usuario con ID " + usuarioId + " no encontrado para la actualización.");
            }
            m.setUsuario(usuario);
        }

        // 3. Actualizar la relación Prestamo si el ID fue proporcionado
        if (prestamoId != null) {
            Prestamo prestamo = prestamoDao.find(prestamoId);
            if (prestamo == null) {
                throw new RuntimeException("Préstamo con ID " + prestamoId + " no encontrado para la actualización.");
            }
            m.setPrestamo(prestamo);
        } else if (prestamoId == null && m.getPrestamo() != null) {
            m.setPrestamo(null); // Si envían null, desvincular
        }

        // 4. Persistir cambios usando el DAO
        return multaDao.update(m);
    }

    // --------------------------------------------------
    // OPERACIÓN: ELIMINAR (DELETE)
    // --------------------------------------------------
    public boolean delete(Integer id) {
        return multaDao.delete(id);
    }
}