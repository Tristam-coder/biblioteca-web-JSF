/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.libreria.service;

/**
 *
 * @author CESAR
 */


import com.libreria.model.Prestamo;
import jakarta.persistence.*;
import java.util.List;

/**
 * Servicio para manejar operaciones CRUD de la entidad Prestamo.
 */
public class PrestamoService {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("AppPU");

    // --------------------------------------------------
    // OPERACIÓN: CREAR (INSERTAR)
    // --------------------------------------------------
    public Prestamo create(Prestamo p) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(p);
            tx.commit();
            return p;
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
    public Prestamo find(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Prestamo.class, id);
        } finally {
            em.close();
        }
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR TODOS (SELECT ALL)
    // --------------------------------------------------
    public List<Prestamo> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT p FROM Prestamo p", Prestamo.class).getResultList();
        } finally {
            em.close();
        }
    }

    // --------------------------------------------------
    // OPERACIÓN: ACTUALIZAR (UPDATE)
    // --------------------------------------------------
    public Prestamo update(Integer id, Prestamo cambios) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Prestamo p = em.find(Prestamo.class, id);

            if (p == null) {
                tx.rollback();
                return null;
            }
            
            // Aplicar cambios:
            p.setUsuario(cambios.getUsuario());
            p.setEjemplar(cambios.getEjemplar());
            p.setFechaPrestamo(cambios.getFechaPrestamo());
            p.setFechaDevolucionPrevista(cambios.getFechaDevolucionPrevista());
            p.setFechaDevolucionReal(cambios.getFechaDevolucionReal());
            p.setEstado(cambios.getEstado());
            p.setMulta(cambios.getMulta());
            p.setObservaciones(cambios.getObservaciones());

            em.merge(p);
            tx.commit();
            return p;
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
            Prestamo p = em.find(Prestamo.class, id);

            if (p == null) {
                tx.rollback();
                return false;
            }
            em.remove(p);
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
