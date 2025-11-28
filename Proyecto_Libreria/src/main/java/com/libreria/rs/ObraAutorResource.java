/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.libreria.rs;

/**
 *
 * @author CESAR
 */

import com.libreria.model.ObraAutor;
import com.libreria.service.ObraAutorService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Path("/obraautores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ObraAutorResource {

    private ObraAutorService service = new ObraAutorService();

    @GET
    public Response listar() {
        List<ObraAutor> all = service.findAll();
        return Response.ok(all).build();
    }

    @GET
    @Path("{id}")
    public Response obtener(@PathParam("id") Integer id) {
        ObraAutor oa = service.find(id);
        if (oa == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(oa).build();
    }

    @POST
    public Response crear(ObraAutor obraAutor, @Context UriInfo uriInfo) {
        ObraAutor creado = service.create(obraAutor);
        URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(creado.getId())).build();
        return Response.created(uri).entity(creado).build();
    }

    @PUT
    @Path("{id}")
    public Response actualizar(@PathParam("id") Integer id, ObraAutor cambios) {
        ObraAutor actualizado = service.update(id, cambios);
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