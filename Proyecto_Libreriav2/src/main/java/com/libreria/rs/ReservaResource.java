package com.libreria.rs;

import com.libreria.model.Reserva;
import com.libreria.dto.ReservaDTO; // Importar DTO
import com.libreria.service.ReservaService;
import jakarta.inject.Inject; // Importar inyección
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Path("/reservas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReservaResource {

    @Inject // 🎯 Usar inyección CDI/EJB
    private ReservaService service;

    @GET
    public Response listar() {
        List<Reserva> all = service.findAll();
        // Mapear Entidades a DTOs
        List<ReservaDTO> dtos = all.stream()
                .map(ReservaDTO::new)
                .collect(Collectors.toList());
        return Response.ok(dtos).build();
    }

    @GET
    @Path("{id}")
    public Response obtener(@PathParam("id") Integer id) {
        Reserva r = service.find(id);
        if (r == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // Retornar el DTO
        return Response.ok(new ReservaDTO(r)).build();
    }

    @POST
    public Response crear(ReservaDTO reservaDTO, @Context UriInfo uriInfo) {
        Reserva reserva = reservaDTO.toEntity();

        try {
            // Llamar al servicio, pasando los IDs de las relaciones
            Reserva creado = service.create(reserva, reservaDTO.getUsuarioId(), reservaDTO.getObraId());

            URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(creado.getId())).build();
            // Retornar el DTO del objeto creado
            return Response.created(uri).entity(new ReservaDTO(creado)).build();
        } catch (RuntimeException e) {
            // Manejo de errores para entidades relacionadas no encontradas
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("{id}")
    public Response actualizar(@PathParam("id") Integer id, ReservaDTO cambiosDTO) {
        Reserva cambios = cambiosDTO.toEntity();

        try {
            // Llamar al servicio, pasando los IDs para actualizar las relaciones
            Reserva actualizado = service.update(id, cambios, cambiosDTO.getUsuarioId(), cambiosDTO.getObraId());

            if (actualizado == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            // Retornar el DTO
            return Response.ok(new ReservaDTO(actualizado)).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
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