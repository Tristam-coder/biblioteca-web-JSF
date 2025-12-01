package com.libreria.service;

import com.libreria.dao.ObraDAO;     // Necesario para la relación
import com.libreria.dao.ReservaDAO;
import com.libreria.dao.UsuarioDAO;  // Necesario para la relación
import com.libreria.model.Obra;
import com.libreria.model.Reserva;
import com.libreria.model.Usuario;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.util.List;

/**
 * Servicio para manejar operaciones CRUD de la entidad Reserva.
 */
@RequestScoped
public class ReservaService {

    @Inject
    private ReservaDAO reservaDao;

    // 🎯 Asumimos que existen los DAOs para las entidades relacionadas:
    @Inject
    private UsuarioDAO usuarioDao;
    @Inject
    private ObraDAO obraDao;

    // --------------------------------------------------
    // OPERACIÓN: CREAR (INSERTAR)
    // --------------------------------------------------
    public Reserva create(Reserva r, Integer usuarioId, Integer obraId) {

        // 1. Obtener entidades relacionadas (Managed Entities)
        Usuario usuario = usuarioDao.find(usuarioId);
        Obra obra = obraDao.find(obraId);

        if (usuario == null) {
            throw new RuntimeException("Usuario con ID " + usuarioId + " no encontrado.");
        }
        if (obra == null) {
            throw new RuntimeException("Obra con ID " + obraId + " no encontrada.");
        }

        // 2. Asignar relaciones
        r.setUsuario(usuario);
        r.setObra(obra);

        // 3. Persistir
        return reservaDao.create(r);
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR POR ID (SELECT)
    // --------------------------------------------------
    public Reserva find(Integer id) {
        return reservaDao.find(id);
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR TODOS (SELECT ALL)
    // --------------------------------------------------
    public List<Reserva> findAll() {
        return reservaDao.findAll();
    }

    // --------------------------------------------------
    // OPERACIÓN: ACTUALIZAR (UPDATE)
    // --------------------------------------------------
    public Reserva update(Integer id, Reserva cambios, Integer usuarioId, Integer obraId) {
        Reserva r = reservaDao.find(id);

        if (r == null) {
            return null;
        }

        // Aplicar cambios de campos directos:
        r.setFechaReserva(cambios.getFechaReserva());
        r.setFechaDisponibilidadEstimada(cambios.getFechaDisponibilidadEstimada());
        r.setEstado(cambios.getEstado());
        r.setPosicionCola(cambios.getPosicionCola());
        r.setNotificado(cambios.getNotificado());


        // 1. Actualizar relación Usuario
        if (usuarioId != null && (r.getUsuario() == null || !r.getUsuario().getId().equals(usuarioId))) {
            Usuario usuario = usuarioDao.find(usuarioId);
            if (usuario == null) throw new RuntimeException("Usuario ID " + usuarioId + " no encontrado.");
            r.setUsuario(usuario);
        }

        // 2. Actualizar relación Obra
        if (obraId != null && (r.getObra() == null || !r.getObra().getId().equals(obraId))) {
            Obra obra = obraDao.find(obraId);
            if (obra == null) throw new RuntimeException("Obra ID " + obraId + " no encontrada.");
            r.setObra(obra);
        }

        return reservaDao.update(r);
    }

    // --------------------------------------------------
    // OPERACIÓN: ELIMINAR (DELETE)
    // --------------------------------------------------
    public boolean delete(Integer id) {
        return reservaDao.delete(id);
    }
}