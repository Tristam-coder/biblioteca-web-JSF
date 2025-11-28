/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.libreria.service;

/**
 *
 * @author CESAR
 */

import com.libreria.model.Multa;
import jakarta.persistence.*;
import java.util.List;

/**
 * Servicio para manejar operaciones CRUD de la entidad Multa.
 */
public class MultaService {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("AppP");

    // --------------------------------------------------
    // OPERACIÓN: CREAR (INSERTAR)
    // --------------------------------------------------
    public Multa create(Multa m) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(m);
            tx.commit();
            return m;
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
    public Multa find(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Multa.class, id);
        } finally {
            em.close();
        }
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR TODOS (SELECT ALL)
    // --------------------------------------------------
    public List<Multa> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT m FROM Multa m", Multa.class).getResultList();
        } finally {
            em.close();
        }
    }

    // --------------------------------------------------
    // OPERACIÓN: ACTUALIZAR (UPDATE)
    // --------------------------------------------------
    public Multa update(Integer id, Multa cambios) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Multa m = em.find(Multa.class, id);

            if (m == null) {
                tx.rollback();
                return null;
            }
            
            // Aplicar cambios:
            m.setUsuario(cambios.getUsuario());
            m.setPrestamo(cambios.getPrestamo());
            m.setMonto(cambios.getMonto());
            m.setMotivo(cambios.getMotivo());
            m.setEstado(cambios.getEstado());
            m.setFechaGeneracion(cambios.getFechaGeneracion());
            m.setFechaPago(cambios.getFechaPago());

            em.merge(m);
            tx.commit();
            return m;
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
            Multa m = em.find(Multa.class, id);

            if (m == null) {
                tx.rollback();
                return false;
            }
            em.remove(m);
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
