package com.libreria.dao;

import com.libreria.model.Usuario;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 * Data Access Object para Usuario. Usa JTA y CDI/EJB para inyección y transacciones.
 */
@Stateless // Importante para que Payara gestione la inyección y las transacciones
public class UsuarioDAO {

    @PersistenceContext(unitName = "AppP") // Inyección del EntityManager
    private EntityManager em;

    // --------------------------------------------------
    // OPERACIÓN: CREAR (INSERTAR) - Transacción gestionada por Payara
    // --------------------------------------------------
    public Usuario create(Usuario u) {
        em.persist(u);
        return u;
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR POR ID (SELECT)
    // --------------------------------------------------
    public Usuario find(Integer id) {
        return em.find(Usuario.class, id);
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR TODOS (SELECT ALL)
    // --------------------------------------------------
    public List<Usuario> findAll() {
        return em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
    }

    // --------------------------------------------------
    // OPERACIÓN: ACTUALIZAR (UPDATE) - Transacción gestionada por Payara
    // --------------------------------------------------
    public Usuario update(Usuario u) {
        return em.merge(u);
    }

    // --------------------------------------------------
    // OPERACIÓN: ELIMINAR (DELETE) - Transacción gestionada por Payara
    // --------------------------------------------------
    public boolean delete(Integer id) {
        Usuario u = em.find(Usuario.class, id);

        if (u == null) {
            return false;
        }
        em.remove(u);
        return true;
    }
}