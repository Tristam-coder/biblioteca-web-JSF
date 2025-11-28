/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.libreria.service;

/**
 *
 * @author CESAR
 */


import com.libreria.model.Autor;
import jakarta.persistence.*;
import java.util.List;

/**
 * Servicio para manejar operaciones CRUD de la entidad Autor.
 */
public class AutorService {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("AppP");

    // --------------------------------------------------
    // OPERACIÓN: CREAR (INSERTAR)
    // --------------------------------------------------
    public Autor create(Autor a) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(a);
            tx.commit();
            return a;
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
    public Autor find(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Autor.class, id);
        } finally {
            em.close();
        }
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR TODOS (SELECT ALL)
    // --------------------------------------------------
    public List<Autor> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT a FROM Autor a", Autor.class).getResultList();
        } finally {
            em.close();
        }
    }

    // --------------------------------------------------
    // OPERACIÓN: ACTUALIZAR (UPDATE)
    // --------------------------------------------------
    public Autor update(Integer id, Autor cambios) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Autor a = em.find(Autor.class, id);

            if (a == null) {
                tx.rollback();
                return null;
            }
            
            // Aplicar cambios:
            a.setNombre(cambios.getNombre());
            a.setApellido(cambios.getApellido());
            a.setFechaNacimiento(cambios.getFechaNacimiento());
            a.setNacionalidad(cambios.getNacionalidad());
            a.setBiografia(cambios.getBiografia());

            em.merge(a);
            tx.commit();
            return a;

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
            Autor a = em.find(Autor.class, id);

            if (a == null) {
                tx.rollback();
                return false;
            }
            em.remove(a);
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
