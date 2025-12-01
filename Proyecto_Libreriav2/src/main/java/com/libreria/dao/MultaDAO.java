package com.libreria.dao;

import com.libreria.model.Multa;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 * Data Access Object para Multa. Usa JTA y CDI/EJB para inyección y transacciones.
 */
@Stateless // Importante para que Payara gestione la inyección y las transacciones
public class MultaDAO {

    @PersistenceContext(unitName = "AppP") // Inyección del EntityManager
    private EntityManager em;

    // --------------------------------------------------
    // OPERACIÓN: CREAR (INSERTAR) - Transacción gestionada por Payara
    // --------------------------------------------------
    public Multa create(Multa m) {
        em.persist(m);
        return m;
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR POR ID (SELECT)
    // --------------------------------------------------
    public Multa find(Integer id) {
        return em.find(Multa.class, id);
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR TODOS (SELECT ALL)
    // --------------------------------------------------
    public List<Multa> findAll() {
        return em.createQuery("SELECT m FROM Multa m", Multa.class).getResultList();
    }

    // --------------------------------------------------
    // OPERACIÓN: ACTUALIZAR (UPDATE) - Transacción gestionada por Payara
    // --------------------------------------------------
    public Multa update(Multa m) {
        return em.merge(m);
    }

    // --------------------------------------------------
    // OPERACIÓN: ELIMINAR (DELETE) - Transacción gestionada por Payara
    // --------------------------------------------------
    public boolean delete(Integer id) {
        Multa m = em.find(Multa.class, id);

        if (m == null) {
            return false;
        }
        em.remove(m);
        return true;
    }
}