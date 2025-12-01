/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.libreria.service;

/**
 *
 * @author CESAR
 */

import com.libreria.model.ObraAutor;
import jakarta.persistence.*;
import java.util.List;

/**
 * Servicio para manejar operaciones CRUD de la entidad ObraAutor.
 */
public class ObraAutorService {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("AppPU");

    // --------------------------------------------------
    // OPERACIÓN: CREAR (INSERTAR)
    // --------------------------------------------------
    public ObraAutor create(ObraAutor oa) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(oa);
            tx.commit();
            return oa;
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
    public ObraAutor find(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(ObraAutor.class, id);
        } finally {
            em.close();
        }
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR TODOS (SELECT ALL)
    // --------------------------------------------------
    public List<ObraAutor> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT oa FROM ObraAutor oa", ObraAutor.class).getResultList();
        } finally {
            em.close();
        }
    }

    // --------------------------------------------------
    // OPERACIÓN: ACTUALIZAR (UPDATE)
    // --------------------------------------------------
    public ObraAutor update(Integer id, ObraAutor cambios) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            ObraAutor oa = em.find(ObraAutor.class, id);

            if (oa == null) {
                tx.rollback();
                return null;
            }
            
            // Aplicar cambios:
            oa.setObra(cambios.getObra());
            oa.setAutor(cambios.getAutor());
            oa.setTipoContribucion(cambios.getTipoContribucion());

            em.merge(oa);
            tx.commit();
            return oa;
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
            ObraAutor oa = em.find(ObraAutor.class, id);

            if (oa == null) {
                tx.rollback();
                return false;
            }
            em.remove(oa);
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