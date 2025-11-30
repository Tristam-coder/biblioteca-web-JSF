// Archivo: com.libreria.service.UsuarioService.java

package com.libreria.service;

import com.libreria.dao.UsuarioDAO; // Importamos el DAO
import com.libreria.model.Usuario;
import java.util.List;

/**
 * Servicio para manejar la lógica de negocio de la entidad Usuario.
 */
public class UsuarioService {

    // Inyectamos o instanciamos el DAO
    private final UsuarioDAO dao = new UsuarioDAO();

    // --------------------------------------------------
    // OPERACIÓN: CREAR (INSERTAR)
    // --------------------------------------------------
    public Usuario create(Usuario u) {
        // Lógica de negocio (ej. validación, hashing de password) iría aquí.
        // Después, llama al DAO para persistir.
        return dao.create(u);
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR POR ID (SELECT)
    // --------------------------------------------------
    public Usuario find(Integer id) {
        return dao.find(id);
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR TODOS (SELECT ALL)
    // --------------------------------------------------
    public List<Usuario> findAll() {
        return dao.findAll();
    }

    // --------------------------------------------------
    // OPERACIÓN: ACTUALIZAR (UPDATE)
    // --------------------------------------------------
    public Usuario update(Integer id, Usuario cambios) {
        // 1. Obtener el usuario existente del DAO
        Usuario u = dao.find(id);

        if (u == null) {
            return null; // No encontrado
        }

        // 2. Aplicar cambios de la DTO/Entidad parcial 'cambios' a la entidad 'u'
        u.setDni(cambios.getDni() != null ? cambios.getDni() : u.getDni());
        u.setNombre(cambios.getNombre() != null ? cambios.getNombre() : u.getNombre());
        // ... y así con todos los campos. Es importante no sobrescribir con null a menos que se quiera borrar el valor.

        // Para este ejemplo, simplificaremos a como lo tenías, asumiendo que 'cambios' es completo:
        u.setDni(cambios.getDni());
        u.setNombre(cambios.getNombre());
        u.setApellido(cambios.getApellido());
        u.setEmail(cambios.getEmail());
        u.setPassword(cambios.getPassword());
        u.setTelefono(cambios.getTelefono());
        u.setDireccion(cambios.getDireccion());
        u.setTipoUsuario(cambios.getTipoUsuario());
        u.setEstado(cambios.getEstado());
        u.setFechaRegistro(cambios.getFechaRegistro());


        // 3. Persistir los cambios a través del DAO
        return dao.update(u);
    }

    // --------------------------------------------------
    // OPERACIÓN: ELIMINAR (DELETE)
    // --------------------------------------------------
    public boolean delete(Integer id) {
        return dao.delete(id);
    }
}