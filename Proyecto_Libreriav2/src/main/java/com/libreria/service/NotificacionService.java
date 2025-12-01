package com.libreria.service;

import com.libreria.dao.NotificacionDAO;
import com.libreria.dao.UsuarioDAO; // Necesario para la relación
import com.libreria.model.Notificacion;
import com.libreria.model.Usuario;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.util.List;

/**
 * Servicio para manejar operaciones CRUD de la entidad Notificacion.
 */
@RequestScoped
public class NotificacionService {

    @Inject // Inyección del DAO
    private NotificacionDAO notificacionDao;

    @Inject // Asumimos que tienes un DAO para obtener la relación Usuario
    private UsuarioDAO usuarioDao;

    // --------------------------------------------------
    // OPERACIÓN: CREAR (INSERTAR)
    // --------------------------------------------------
    public Notificacion create(Notificacion n, Integer usuarioId) {

        // 1. Obtener entidad Usuario para establecer la relación
        Usuario usuario = usuarioDao.find(usuarioId);

        if (usuario == null) {
            throw new RuntimeException("Usuario con ID " + usuarioId + " no encontrado.");
        }

        // 2. Establecer la relación
        n.setUsuario(usuario);

        // 3. Persistir
        return notificacionDao.create(n);
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR POR ID (SELECT)
    // --------------------------------------------------
    public Notificacion find(Integer id) {
        return notificacionDao.find(id);
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR TODOS (SELECT ALL)
    // --------------------------------------------------
    public List<Notificacion> findAll() {
        return notificacionDao.findAll();
    }

    // --------------------------------------------------
    // OPERACIÓN: ACTUALIZAR (UPDATE)
    // --------------------------------------------------
    public Notificacion update(Integer id, Notificacion cambios, Integer usuarioId) {
        Notificacion n = notificacionDao.find(id);

        if (n == null) {
            return null;
        }

        // Aplicar cambios:
        n.setTipo(cambios.getTipo());
        n.setMensaje(cambios.getMensaje());
        n.setLeida(cambios.getLeida());
        n.setFechaEnvio(cambios.getFechaEnvio()); // Usa el valor del DTO convertido

        // 1. Actualizar relación Usuario si el ID fue proporcionado/cambiado
        if (usuarioId != null && (n.getUsuario() == null || !n.getUsuario().getId().equals(usuarioId))) {
            Usuario usuario = usuarioDao.find(usuarioId);
            if (usuario == null) {
                throw new RuntimeException("Usuario con ID " + usuarioId + " no encontrado para la actualización.");
            }
            n.setUsuario(usuario);
        }

        return notificacionDao.update(n);
    }

    // --------------------------------------------------
    // OPERACIÓN: ELIMINAR (DELETE)
    // --------------------------------------------------
    public boolean delete(Integer id) {
        return notificacionDao.delete(id);
    }
}