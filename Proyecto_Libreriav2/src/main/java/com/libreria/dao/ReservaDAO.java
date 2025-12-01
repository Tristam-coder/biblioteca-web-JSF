package com.libreria.dao;

import com.libreria.model.Reserva;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 * Data Access Object para Reserva. Usa JTA y CDI/EJB.
 */
@Stateless
public class ReservaDAO {

    @PersistenceContext(unitName = "AppP")
    private EntityManager em;

    // --------------------------------------------------
    // OPERACIÓN: CREAR (INSERTAR)
    // --------------------------------------------------
    public Reserva create(Reserva r) {
        em.persist(r);
        return r;
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR POR ID (SELECT)
    // --------------------------------------------------
    public Reserva find(Integer id) {
        return em.find(Reserva.class, id);
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR TODOS (SELECT ALL)
    // --------------------------------------------------
    public List<Reserva> findAll() {
        return em.createQuery("SELECT r FROM Reserva r", Reserva.class).getResultList();
    }

    // --------------------------------------------------
    // OPERACIÓN: ACTUALIZAR (UPDATE)
    // --------------------------------------------------
    public Reserva update(Reserva r) {
        return em.merge(r);
    }

    // --------------------------------------------------
    // OPERACIÓN: ELIMINAR (DELETE)
    // --------------------------------------------------
    public boolean delete(Integer id) {
        Reserva r = em.find(Reserva.class, id);

        if (r == null) {
            return false;
        }
        em.remove(r);
        return true;
    }
}