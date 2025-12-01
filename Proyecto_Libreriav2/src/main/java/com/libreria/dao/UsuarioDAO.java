package com.libreria.dao;

import com.libreria.model.Usuario;
import jakarta.persistence.*;
import java.util.List;

/**
 * Data Access Object para la entidad Usuario.
 * Maneja la lógica de persistencia y la interacción directa con la DB.
 */
public class UsuarioDAO {

    // Se mantiene el factory aquí, es responsable del acceso a la DB.
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("AppP");

    /**
     * @return El EntityManagerFactory estático para reutilización.
     */
    protected EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }

    // --------------------------------------------------
    // OPERACIÓN: CREAR (INSERTAR)
    // --------------------------------------------------
    public Usuario create(Usuario u) {
        EntityManager em = getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(u);
            tx.commit();
            // La entidad ahora tiene su ID generado
            return u;
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
    public Usuario find(Integer id) {
        EntityManager em = getEntityManagerFactory().createEntityManager();
        try {
            // Retorna la entidad Usuario gestionada o null
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR TODOS (SELECT ALL)
    // --------------------------------------------------
    public List<Usuario> findAll() {
        EntityManager em = getEntityManagerFactory().createEntityManager();
        try {
            // Retorna una lista de entidades Usuario
            return em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
        } finally {
            em.close();
        }
    }

    // --------------------------------------------------
    // OPERACIÓN: ACTUALIZAR (UPDATE)
    // --------------------------------------------------
    public Usuario update(Usuario u) {
        EntityManager em = getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            // em.merge(u) devuelve la entidad gestionada y actualizada
            Usuario merged = em.merge(u);
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
            Usuario u = em.find(Usuario.class, id);

            if (u == null) {
                tx.rollback();
                return false;
            }
            // Importante: La entidad debe estar gestionada para poder eliminarla
            em.remove(u);
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