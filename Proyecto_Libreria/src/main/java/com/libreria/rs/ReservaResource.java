/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.libreria.rs;

/**
 *
 * @author CESAR
 */


import com.libreria.model.Reserva;
import com.libreria.service.ReservaService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Path("/reservas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReservaResource {

    private ReservaService service = new ReservaService();

    @GET
    public Response listar() {
        List<Reserva> all = service.findAll();
        return Response.ok(all).build();
    }

    @GET
    @Path("{id}")
    public Response obtener(@PathParam("id") Integer id) {
        Reserva r = service.find(id);
        if (r == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(r).build();
    }

    @POST
    public Response crear(Reserva reserva, @Context UriInfo uriInfo) {
        Reserva creado = service.create(reserva);
        URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(creado.getId())).build();
        return Response.created(uri).entity(creado).build();
    }

    @PUT
    @Path("{id}")
    public Response actualizar(@PathParam("id") Integer id, Reserva cambios) {
        Reserva actualizado = service.update(id, cambios);
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