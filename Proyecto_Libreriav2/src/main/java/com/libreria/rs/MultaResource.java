/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.libreria.rs;

/**
 *
 * @author CESAR
 */


import com.libreria.model.Multa;
import com.libreria.service.MultaService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Path("/multas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MultaResource {

    private MultaService service = new MultaService();

    @GET
    public Response listar() {
        List<Multa> all = service.findAll();
        return Response.ok(all).build();
    }

    @GET
    @Path("{id}")
    public Response obtener(@PathParam("id") Integer id) {
        Multa m = service.find(id);
        if (m == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(m).build();
    }

    @POST
    public Response crear(Multa multa, @Context UriInfo uriInfo) {
        Multa creado = service.create(multa);
        URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(creado.getId())).build();
        return Response.created(uri).entity(creado).build();
    }

    @PUT
    @Path("{id}")
    public Response actualizar(@PathParam("id") Integer id, Multa cambios) {
        Multa actualizado = service.update(id, cambios);
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