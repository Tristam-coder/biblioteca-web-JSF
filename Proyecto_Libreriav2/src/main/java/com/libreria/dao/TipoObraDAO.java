package com.libreria.dao;

import com.libreria.model.TipoObra;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 * Data Access Object para TipoObra. Usa JTA y CDI/EJB.
 */
@Stateless
public class TipoObraDAO {

    @PersistenceContext(unitName = "AppP")
    private EntityManager em;

    // --------------------------------------------------
    // OPERACIÓN: CREAR (INSERTAR)
    // --------------------------------------------------
    public TipoObra create(TipoObra t) {
        em.persist(t);
        return t;
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR POR ID (SELECT)
    // --------------------------------------------------
    public TipoObra find(Integer id) {
        return em.find(TipoObra.class, id);
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR TODOS (SELECT ALL)
    // --------------------------------------------------
    public List<TipoObra> findAll() {
        return em.createQuery("SELECT t FROM TipoObra t", TipoObra.class).getResultList();
    }

    // --------------------------------------------------
    // OPERACIÓN: ACTUALIZAR (UPDATE)
    // --------------------------------------------------
    public TipoObra update(TipoObra t) {
        return em.merge(t);
    }

    // --------------------------------------------------
    // OPERACIÓN: ELIMINAR (DELETE)
    // --------------------------------------------------
    public boolean delete(Integer id) {
        TipoObra t = em.find(TipoObra.class, id);

        if (t == null) {
            return false;
        }
        em.remove(t);
        return true;
    }
}