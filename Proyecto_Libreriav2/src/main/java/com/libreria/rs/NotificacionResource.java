package com.libreria.rs;

import com.libreria.model.Notificacion;
import com.libreria.dto.NotificacionDTO;
import com.libreria.service.NotificacionService;
import jakarta.inject.Inject; // Importante: Usar inyección
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Path("/notificaciones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NotificacionResource {

    @Inject // 🎯 Usar inyección CDI/EJB
    private NotificacionService service;

    @GET
    public Response listar() {
        List<Notificacion> all = service.findAll();
        // Mapear Entidades a DTOs
        List<NotificacionDTO> dtos = all.stream()
                .map(NotificacionDTO::new)
                .collect(Collectors.toList());
        return Response.ok(dtos).build();
    }

    @GET
    @Path("{id}")
    public Response obtener(@PathParam("id") Integer id) {
        Notificacion n = service.find(id);
        if (n == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // Retornar el DTO
        return Response.ok(new NotificacionDTO(n)).build();
    }

    @POST
    public Response crear(NotificacionDTO notificacionDTO, @Context UriInfo uriInfo) {

        Notificacion notificacion = notificacionDTO.toEntity();

        try {
            // Llamar al servicio, pasando el ID de la relación
            Notificacion creado = service.create(notificacion, notificacionDTO.getUsuarioId());

            URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(creado.getId())).build();
            // Retornar el DTO del objeto creado
            return Response.created(uri).entity(new NotificacionDTO(creado)).build();
        } catch (RuntimeException e) {
            // Manejo de error para Usuario no encontrado
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("{id}")
    public Response actualizar(@PathParam("id") Integer id, NotificacionDTO cambiosDTO) {

        Notificacion cambios = cambiosDTO.toEntity();

        try {
            // Llamar al servicio, pasando el ID para actualizar la relación
            Notificacion actualizado = service.update(id, cambios, cambiosDTO.getUsuarioId());

            if (actualizado == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            // Retornar el DTO
            return Response.ok(new NotificacionDTO(actualizado)).build();
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