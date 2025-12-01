/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.libreria.rs;

/**
 *
 * @author CESAR
 */

import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.ApplicationPath;
import java.util.Set;
import java.util.HashSet;

@ApplicationPath("/api")
public class RestApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> s = new HashSet<>();
        // Agregar todas las clases Resource
        s.add(AutorResource.class);
        s.add(EditorialResource.class);
        s.add(EjemplarResource.class);
        s.add(HistorialResource.class);
        s.add(MultaResource.class);
        s.add(NotificacionResource.class);
        s.add(ObraResource.class);
        s.add(ObraAutorResource.class);
        s.add(PrestamoResource.class);
        s.add(ReservaResource.class);
        s.add(TipoObraResource.class);
        s.add(UsuarioResource.class);
        // s.add(RegistroResource.class); // (Si aún la usas)
        return s;
    }
}
