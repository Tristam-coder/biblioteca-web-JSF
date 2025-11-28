/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.libreria.service;

/**
 *
 * @author CESAR
 */

import com.libreria.model.Ejemplar;
import jakarta.persistence.*;
import java.util.List;

/**
 * Servicio para manejar operaciones CRUD de la entidad Ejemplar.
 */
public class EjemplarService {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("AppP");

    // --------------------------------------------------
    // OPERACIÓN: CREAR (INSERTAR)
    // --------------------------------------------------
    public Ejemplar create(Ejemplar e) {
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
    public Ejemplar find(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Ejemplar.class, id);
        } finally {
            em.close();
        }
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR TODOS (SELECT ALL)
    // --------------------------------------------------
    public List<Ejemplar> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT e FROM Ejemplar e", Ejemplar.class).getResultList();
        } finally {
            em.close();
        }
    }

    // --------------------------------------------------
    // OPERACIÓN: ACTUALIZAR (UPDATE)
    // --------------------------------------------------
    public Ejemplar update(Integer id, Ejemplar cambios) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Ejemplar e = em.find(Ejemplar.class, id);

            if (e == null) {
                tx.rollback();
                return null;
            }
            
            // Aplicar cambios:
            e.setObra(cambios.getObra()); // Asume que la Obra está gestionada o se hace merge.
            e.setCodigoBarras(cambios.getCodigoBarras());
            e.setEstado(cambios.getEstado());
            e.setUbicacion(cambios.getUbicacion());
            e.setObservaciones(cambios.getObservaciones());

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
