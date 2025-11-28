/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.libreria.service;

/**
 *
 * @author CESAR
 */

import com.libreria.model.Editorial;
import jakarta.persistence.*;
import java.util.List;

/**
 * Servicio para manejar operaciones CRUD de la entidad Editorial.
 */
public class EditorialService {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("AppP");

    // --------------------------------------------------
    // OPERACIÓN: CREAR (INSERTAR)
    // --------------------------------------------------
    public Editorial create(Editorial e) {
        EntityManager em = emf.createEntityManager();
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
        EntityManager em = emf.createEntityManager();
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
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT e FROM Editorial e", Editorial.class).getResultList();
        } finally {
            em.close();
        }
    }

    // --------------------------------------------------
    // OPERACIÓN: ACTUALIZAR (UPDATE)
    // --------------------------------------------------
    public Editorial update(Integer id, Editorial cambios) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Editorial e = em.find(Editorial.class, id);

            if (e == null) {
                tx.rollback();
                return null;
            }
            
            // Aplicar cambios:
            e.setNombre(cambios.getNombre());
            e.setDireccion(cambios.getDireccion());
            e.setTelefono(cambios.getTelefono());
            e.setEmail(cambios.getEmail());
            e.setFechaFundacion(cambios.getFechaFundacion());
            e.setPais(cambios.getPais());

            em.merge(e);
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
    // OPERACIÓN: ELIMINAR (DELETE)
    // --------------------------------------------------
    public boolean delete(Integer id) {
        EntityManager em = emf.createEntityManager();
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
