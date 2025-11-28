/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.libreria.service;

/**
 *
 * @author CESAR
 */

import com.libreria.model.Obra;
import jakarta.persistence.*;
import java.util.List;

/**
 * Servicio para manejar operaciones CRUD de la entidad Obra.
 */
public class ObraService {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("AppPU");

    // --------------------------------------------------
    // OPERACIÓN: CREAR (INSERTAR)
    // --------------------------------------------------
    public Obra create(Obra o) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(o);
            tx.commit();
            return o;
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
    public Obra find(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Obra.class, id);
        } finally {
            em.close();
        }
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR TODOS (SELECT ALL)
    // --------------------------------------------------
    public List<Obra> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT o FROM Obra o", Obra.class).getResultList();
        } finally {
            em.close();
        }
    }

    // --------------------------------------------------
    // OPERACIÓN: ACTUALIZAR (UPDATE)
    // --------------------------------------------------
    public Obra update(Integer id, Obra cambios) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Obra o = em.find(Obra.class, id);

            if (o == null) {
                tx.rollback();
                return null;
            }
            
            // Aplicar cambios:
            o.setTitulo(cambios.getTitulo());
            o.setIsbnIssn(cambios.getIsbnIssn());
            o.setTipoObra(cambios.getTipoObra());
            o.setEditorial(cambios.getEditorial());
            o.setAnioPublicacion(cambios.getAnioPublicacion());
            o.setEdicion(cambios.getEdicion());
            o.setNumeroPaginas(cambios.getNumeroPaginas());
            o.setIdioma(cambios.getIdioma());
            o.setAreaTematica(cambios.getAreaTematica());
            o.setNivel(cambios.getNivel());
            o.setDescripcion(cambios.getDescripcion());
            o.setEstado(cambios.getEstado());

            em.merge(o);
            tx.commit();
            return o;
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
            Obra o = em.find(Obra.class, id);

            if (o == null) {
                tx.rollback();
                return false;
            }
            em.remove(o);
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
