package com.libreria.dao;

import com.libreria.model.Editorial;
import jakarta.persistence.*;
import java.util.List;

/**
 * Data Access Object para la entidad Editorial.
 * Maneja la interacción directa con la DB.
 */
public class EditorialDAO {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("AppP");

    protected EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }

    // --------------------------------------------------
    // OPERACIÓN: CREAR (INSERTAR)
    // --------------------------------------------------
    public Editorial create(Editorial e) {
        EntityManager em = getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(e);
            tx.commit();
            return e;
        } catch (RuntimeException ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR POR ID (SELECT)
    // --------------------------------------------------
    public Editorial find(Integer id) {
        EntityManager em = getEntityManagerFactory().createEntityManager();
        try {
            return em.find(Editorial.class, id);
        } finally {
            em.close();
        }
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR TODOS (SELECT ALL)
    // --------------------------------------------------
    public List<Editorial> findAll() {
        EntityManager em = getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("SELECT e FROM Editorial e", Editorial.class).getResultList();
        } finally {
            em.close();
        }
    }

    // --------------------------------------------------
    // OPERACIÓN: ACTUALIZAR (UPDATE)
    // --------------------------------------------------
    public Editorial update(Editorial e) {
        EntityManager em = getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Editorial merged = em.merge(e);
            tx.commit();
            return merged;
        } catch (RuntimeException ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    // --------------------------------------------------
    // OPERACIÓN: ELIMINAR (DELETE)
    // --------------------------------------------------
    public boolean delete(Integer id) {
        EntityManager em = getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Editorial e = em.find(Editorial.class, id);

            if (e == null) {
                tx.rollback();
                return false;
            }
            em.remove(e);
            tx.commit();
            return true;
        } catch (RuntimeException ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }
}