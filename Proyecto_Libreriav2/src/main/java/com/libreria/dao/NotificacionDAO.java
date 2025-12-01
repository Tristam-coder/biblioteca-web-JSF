package com.libreria.dao;

import com.libreria.model.Notificacion;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 * Data Access Object para Notificacion. Usa JTA y CDI/EJB.
 */
@Stateless
public class NotificacionDAO {

    @PersistenceContext(unitName = "AppP")
    private EntityManager em;

    // --------------------------------------------------
    // OPERACIÓN: CREAR (INSERTAR)
    // --------------------------------------------------
    public Notificacion create(Notificacion n) {
        // La persistencia es gestionada transaccionalmente por el contenedor (JTA)
        em.persist(n);
        return n;
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR POR ID (SELECT)
    // --------------------------------------------------
    public Notificacion find(Integer id) {
        return em.find(Notificacion.class, id);
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR TODOS (SELECT ALL)
    // --------------------------------------------------
    public List<Notificacion> findAll() {
        return em.createQuery("SELECT n FROM Notificacion n", Notificacion.class).getResultList();
    }

    // --------------------------------------------------
    // OPERACIÓN: ACTUALIZAR (UPDATE)
    // --------------------------------------------------
    public Notificacion update(Notificacion n) {
        return em.merge(n);
    }

    // --------------------------------------------------
    // OPERACIÓN: ELIMINAR (DELETE)
    // --------------------------------------------------
    public boolean delete(Integer id) {
        Notificacion n = em.find(Notificacion.class, id);

        if (n == null) {
            return false;
        }
        em.remove(n);
        return true;
    }
}