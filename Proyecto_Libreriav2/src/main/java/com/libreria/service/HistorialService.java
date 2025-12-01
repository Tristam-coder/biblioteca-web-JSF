package com.libreria.service;

import com.libreria.dao.HistorialDAO;
import com.libreria.model.Historial;
import com.libreria.model.Usuario; // Necesario para gestionar la relación
import java.util.List;

/**
 * Servicio para manejar operaciones CRUD de la entidad Historial.
 */
public class HistorialService {

    private final HistorialDAO dao = new HistorialDAO();
    // NOTA: Asumimos la existencia de UsuarioService o UsuarioDAO para buscar el Usuario relacionado.
    // private final UsuarioService usuarioService = new UsuarioService();

    // Helper para simular la búsqueda de Usuario
    private Usuario findUsuarioById(Integer usuarioId) {
        // En un caso real: return usuarioService.find(usuarioId);
        // Placeholder:
        if (usuarioId != null) {
            // En un entorno JPA, usarías em.getReference(Usuario.class, usuarioId) o em.find()
            Usuario usuario = new Usuario();
            usuario.setId(usuarioId);
            return usuario;
        }
        return null;
    }

    // --------------------------------------------------
    // OPERACIÓN: CREAR (INSERTAR)
    // --------------------------------------------------
    public Historial create(Historial h, Integer usuarioId) {
        // Lógica de negocio: establecer el Usuario a partir del ID
        Usuario usuario = findUsuarioById(usuarioId);
        if (usuario == null && usuarioId != null) {
            throw new RuntimeException("Usuario con ID " + usuarioId + " no encontrado para el registro de Historial.");
        }
        h.setUsuario(usuario);

        return dao.create(h);
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR POR ID (SELECT)
    // --------------------------------------------------
    public Historial find(Integer id) {
        return dao.find(id);
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR TODOS (SELECT ALL)
    // --------------------------------------------------
    public List<Historial> findAll() {
        return dao.findAll();
    }

    // --------------------------------------------------
    // OPERACIÓN: ACTUALIZAR (UPDATE)
    // --------------------------------------------------
    public Historial update(Integer id, Historial cambios, Integer usuarioId) {

        Historial h = dao.find(id);

        if (h == null) {
            return null;
        }

        // 1. Aplicar cambios a los campos directos
        h.setAccion(cambios.getAccion());
        h.setTablaAfectada(cambios.getTablaAfectada());
        h.setRegistroId(cambios.getRegistroId());
        h.setDetalles(cambios.getDetalles());

        // 2. Actualizar la relación Usuario si el ID fue proporcionado
        if (usuarioId != null) {
            Usuario usuario = findUsuarioById(usuarioId);
            if (usuario == null) {
                throw new RuntimeException("Usuario con ID " + usuarioId + " no encontrado para la actualización.");
            }
            h.setUsuario(usuario);
        }

        // 3. Persistir cambios usando el DAO
        return dao.update(h);
    }

    // --------------------------------------------------
    // OPERACIÓN: ELIMINAR (DELETE)
    // --------------------------------------------------
    public boolean delete(Integer id) {
        return dao.delete(id);
    }
}