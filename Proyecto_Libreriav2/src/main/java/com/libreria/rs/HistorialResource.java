/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.libreria.rs;

/**
 *
 * @author CESAR
 */

import com.libreria.model.Historial;
import com.libreria.service.HistorialService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Path("/historiales")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HistorialResource {

    private HistorialService service = new HistorialService();

    @GET
    public Response listar() {
        List<Historial> all = service.findAll();
        return Response.ok(all).build();
    }

    @GET
    @Path("{id}")
    public Response obtener(@PathParam("id") Integer id) {
        Historial h = service.find(id);
        if (h == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(h).build();
    }

    @POST
    public Response crear(Historial historial, @Context UriInfo uriInfo) {
        Historial creado = service.create(historial);
        URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(creado.getId())).build();
        return Response.created(uri).entity(creado).build();
    }

    @PUT
    @Path("{id}")
    public Response actualizar(@PathParam("id") Integer id, Historial cambios) {
        Historial actualizado = service.update(id, cambios);
        if (actualizado == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(actualizado).build();
    }

    @DELETE
    @Path("{id}")
    public Response borrar(@PathParam("id") Integer id) {
        boolean ok = service.delete(id);
        if (!ok) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }
}