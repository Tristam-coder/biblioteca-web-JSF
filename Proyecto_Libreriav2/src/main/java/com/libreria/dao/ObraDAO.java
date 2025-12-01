package com.libreria.dao;

import com.libreria.model.Obra;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 * Data Access Object para Obra. Usa JTA y CDI/EJB.
 */
@Stateless
public class ObraDAO {

    @PersistenceContext(unitName = "AppP")
    private EntityManager em;

    // --------------------------------------------------
    // OPERACIÓN: CREAR (INSERTAR)
    // --------------------------------------------------
    public Obra create(Obra o) {
        // La persistencia es gestionada transaccionalmente por el contenedor
        em.persist(o);
        return o;
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR POR ID (SELECT)
    // --------------------------------------------------
    public Obra find(Integer id) {
        return em.find(Obra.class, id);
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR TODOS (SELECT ALL)
    // --------------------------------------------------
    public List<Obra> findAll() {
        return em.createQuery("SELECT o FROM Obra o", Obra.class).getResultList();
    }

    // --------------------------------------------------
    // OPERACIÓN: ACTUALIZAR (UPDATE)
    // --------------------------------------------------
    public Obra update(Obra o) {
        return em.merge(o);
    }

    // --------------------------------------------------
    // OPERACIÓN: ELIMINAR (DELETE)
    // --------------------------------------------------
    public boolean delete(Integer id) {
        Obra o = em.find(Obra.class, id);

        if (o == null) {
            return false;
        }
        em.remove(o);
        return true;
    }
}