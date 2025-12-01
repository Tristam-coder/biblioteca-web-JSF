package com.libreria.dao;

import com.libreria.model.Ejemplar;
import jakarta.persistence.*;
import java.util.List;

/**
 * Data Access Object para la entidad Ejemplar.
 */
public class EjemplarDAO {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("AppP");

    protected EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }

    // --------------------------------------------------
    // OPERACIÓN: CREAR (INSERTAR)
    // --------------------------------------------------
    public Ejemplar create(Ejemplar e) {
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
    public Ejemplar find(Integer id) {
        EntityManager em = getEntityManagerFactory().createEntityManager();
        try {
            // Se puede usar un join fetch si Obra fuera LAZY y se necesitara cargar
            // return em.createQuery("SELECT e FROM Ejemplar e JOIN FETCH e.obra WHERE e.id = :id", Ejemplar.class)
            //          .setParameter("id", id).getSingleResult();
            // Pero find es suficiente si la Obra se gestiona por separado o si es EAGER.
            return em.find(Ejemplar.class, id);
        } finally {
            em.close();
        }
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR TODOS (SELECT ALL)
    // --------------------------------------------------
    public List<Ejemplar> findAll() {
        EntityManager em = getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("SELECT e FROM Ejemplar e", Ejemplar.class).getResultList();
        } finally {
            em.close();
        }
    }

    // --------------------------------------------------
    // OPERACIÓN: ACTUALIZAR (UPDATE)
    // --------------------------------------------------
    public Ejemplar update(Ejemplar e) {
        EntityManager em = getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Ejemplar merged = em.merge(e);
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
            Ejemplar e = em.find(Ejemplar.class, id);

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