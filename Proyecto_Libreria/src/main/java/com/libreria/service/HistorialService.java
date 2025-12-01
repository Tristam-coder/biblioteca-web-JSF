/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.libreria.service;

/**
 *
 * @author CESAR
 */

import com.libreria.model.Historial;
import jakarta.persistence.*;
import java.util.List;

/**
 * Servicio para manejar operaciones CRUD de la entidad Historial.
 */
public class HistorialService {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("AppPU");

    // --------------------------------------------------
    // OPERACIÓN: CREAR (INSERTAR)
    // --------------------------------------------------
    public Historial create(Historial h) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(h);
            tx.commit();
            return h;
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
    public Historial find(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Historial.class, id);
        } finally {
            em.close();
        }
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR TODOS (SELECT ALL)
    // --------------------------------------------------
    public List<Historial> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT h FROM Historial h", Historial.class).getResultList();
        } finally {
            em.close();
        }
    }

    // --------------------------------------------------
    // OPERACIÓN: ACTUALIZAR (UPDATE)
    // --------------------------------------------------
    public Historial update(Integer id, Historial cambios) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Historial h = em.find(Historial.class, id);

            if (h == null) {
                tx.rollback();
                return null;
            }
            
            // Aplicar cambios:
            h.setUsuario(cambios.getUsuario());
            h.setAccion(cambios.getAccion());
            h.setTablaAfectada(cambios.getTablaAfectada());
            h.setRegistroId(cambios.getRegistroId());
            h.setDetalles(cambios.getDetalles());

            em.merge(h);
            tx.commit();
            return h;
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
            Historial h = em.find(Historial.class, id);

            if (h == null) {
                tx.rollback();
                return false;
            }
            em.remove(h);
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