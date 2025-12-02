package com.libreria.service;

import com.libreria.dao.UsuarioDAO;
import com.libreria.model.Usuario;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.util.List;

/**
 * Servicio para manejar operaciones CRUD de la entidad Usuario.
 */
@RequestScoped // Importante para la inyección (CDI)
public class UsuarioService {

    @Inject
    private UsuarioDAO usuarioDao;

    // --------------------------------------------------
    // OPERACIÓN: CREAR (INSERTAR)
    // --------------------------------------------------
    public Usuario create(Usuario u) {
        return usuarioDao.create(u);
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR POR ID (SELECT)
    // --------------------------------------------------
    public Usuario find(Integer id) {
        return usuarioDao.find(id);
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR TODOS (SELECT ALL)
    // --------------------------------------------------
    public List<Usuario> findAll() {
        return usuarioDao.findAll();
    }

    // --------------------------------------------------
    // OPERACIÓN: ACTUALIZAR (UPDATE)
    // --------------------------------------------------
    public Usuario update(Integer id, Usuario cambios) {
        Usuario u = usuarioDao.find(id);

        if (u == null) {
            return null;
        }

        // Aplicar cambios a campos directos (excluir password)
        u.setDni(cambios.getDni());
        u.setNombre(cambios.getNombre());
        u.setApellido(cambios.getApellido());
        u.setEmail(cambios.getEmail());
        u.setTelefono(cambios.getTelefono());
        u.setDireccion(cambios.getDireccion());
        u.setTipoUsuario(cambios.getTipoUsuario());
        u.setEstado(cambios.getEstado());
        u.setFechaRegistro(cambios.getFechaRegistro());

        return usuarioDao.update(u);
    }

    // --------------------------------------------------
    // OPERACIÓN: ELIMINAR (DELETE)
    // --------------------------------------------------
    public boolean delete(Integer id) {
        return usuarioDao.delete(id);
    }
}