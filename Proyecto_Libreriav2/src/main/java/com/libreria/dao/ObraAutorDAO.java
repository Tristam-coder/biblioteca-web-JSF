package com.libreria.dao;

import com.libreria.model.ObraAutor;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 * Data Access Object para ObraAutor. Usa JTA y CDI/EJB.
 */
@Stateless
public class ObraAutorDAO {

    @PersistenceContext(unitName = "AppP")
    private EntityManager em;

    // --------------------------------------------------
    // OPERACIÓN: CREAR (INSERTAR)
    // --------------------------------------------------
    public ObraAutor create(ObraAutor oa) {
        em.persist(oa);
        return oa;
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR POR ID (SELECT)
    // --------------------------------------------------
    public ObraAutor find(Integer id) {
        return em.find(ObraAutor.class, id);
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR TODOS (SELECT ALL)
    // --------------------------------------------------
    public List<ObraAutor> findAll() {
        return em.createQuery("SELECT oa FROM ObraAutor oa", ObraAutor.class).getResultList();
    }

    // --------------------------------------------------
    // OPERACIÓN: ACTUALIZAR (UPDATE)
    // --------------------------------------------------
    public ObraAutor update(ObraAutor oa) {
        return em.merge(oa);
    }

    // --------------------------------------------------
    // OPERACIÓN: ELIMINAR (DELETE)
    // --------------------------------------------------
    public boolean delete(Integer id) {
        ObraAutor oa = em.find(ObraAutor.class, id);

        if (oa == null) {
            return false;
        }
        em.remove(oa);
        return true;
    }
}