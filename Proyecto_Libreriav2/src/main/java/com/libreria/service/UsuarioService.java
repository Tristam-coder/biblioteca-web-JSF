/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.libreria.service;

/**
 *
 * @author CESAR
 */

import com.libreria.model.Usuario;
import jakarta.persistence.*;
import java.util.List;

/**
 * Servicio para manejar operaciones CRUD de la entidad Usuario.
 */
public class UsuarioService {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("AppP");

    // --------------------------------------------------
    // OPERACIÓN: CREAR (INSERTAR)
    // --------------------------------------------------
    public Usuario create(Usuario u) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(u);
            tx.commit();
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
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    // --------------------------------------------------
    // OPERACIÓN: ENCONTRAR TODOS (SELECT ALL)
    // --------------------------------------------------
    public List<Usuario> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
        } finally {
            em.close();
        }
    }

    // --------------------------------------------------
    // OPERACIÓN: ACTUALIZAR (UPDATE)
    // --------------------------------------------------
    public Usuario update(Integer id, Usuario cambios) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Usuario u = em.find(Usuario.class, id);

            if (u == null) {
                tx.rollback();
                return null;
            }
            
            // Aplicar cambios:
            u.setDni(cambios.getDni());
            u.setNombre(cambios.getNombre());
            u.setApellido(cambios.getApellido());
            u.setEmail(cambios.getEmail());
            u.setPassword(cambios.getPassword());
            u.setTelefono(cambios.getTelefono());
            u.setDireccion(cambios.getDireccion());
            u.setTipoUsuario(cambios.getTipoUsuario());
            u.setEstado(cambios.getEstado());
            u.setFechaRegistro(cambios.getFechaRegistro());

            em.merge(u);
            tx.commit();
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
    // OPERACIÓN: ELIMINAR (DELETE)
    // --------------------------------------------------
    public boolean delete(Integer id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Usuario u = em.find(Usuario.class, id);

            if (u == null) {
                tx.rollback();
                return false;
            }
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
