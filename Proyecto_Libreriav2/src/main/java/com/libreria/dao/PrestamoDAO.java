package com.libreria.dao;

import com.libreria.model.Prestamo;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 * Data Access Object para Prestamo. Usa JTA y CDI/EJB.
 */
@Stateless
public class PrestamoDAO {

    @PersistenceContext(unitName = "AppP")
    private EntityManager em;

    // --------------------------------------------------
    // OPERACIÓN: CREAR (INSERTAR)
    // --------------------------------------------------
    public Prestamo create(Prestamo p) {
        // La persistencia es gestionada transaccionalmente por el contenedor
        em.persist(p);
        return p;
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR POR ID (SELECT)
    // --------------------------------------------------
    public Prestamo find(Integer id) {
        return em.find(Prestamo.class, id);
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR TODOS (SELECT ALL)
    // --------------------------------------------------
    public List<Prestamo> findAll() {
        return em.createQuery("SELECT p FROM Prestamo p", Prestamo.class).getResultList();
    }

    // --------------------------------------------------
    // OPERACIÓN: ACTUALIZAR (UPDATE)
    // --------------------------------------------------
    public Prestamo update(Prestamo p) {
        return em.merge(p);
    }

    // --------------------------------------------------
    // OPERACIÓN: ELIMINAR (DELETE)
    // --------------------------------------------------
    public boolean delete(Integer id) {
        Prestamo p = em.find(Prestamo.class, id);

        if (p == null) {
            return false;
        }
        em.remove(p);
        return true;
    }
}