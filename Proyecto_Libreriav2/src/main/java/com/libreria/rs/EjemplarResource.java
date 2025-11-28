/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.libreria.rs;

/**
 *
 * @author CESAR
 */


import com.libreria.model.Ejemplar;
import com.libreria.service.EjemplarService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Path("/ejemplares")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EjemplarResource {

    private EjemplarService service = new EjemplarService();

    @GET
    public Response listar() {
        List<Ejemplar> all = service.findAll();
        return Response.ok(all).build();
    }

    @GET
    @Path("{id}")
    public Response obtener(@PathParam("id") Integer id) {
        Ejemplar e = service.find(id);
        if (e == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(e).build();
    }

    @POST
    public Response crear(Ejemplar ejemplar, @Context UriInfo uriInfo) {
        Ejemplar creado = service.create(ejemplar);
        URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(creado.getId())).build();
        return Response.created(uri).entity(creado).build();
    }

    @PUT
    @Path("{id}")
    public Response actualizar(@PathParam("id") Integer id, Ejemplar cambios) {
        Ejemplar actualizado = service.update(id, cambios);
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