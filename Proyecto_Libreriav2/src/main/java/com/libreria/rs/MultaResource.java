package com.libreria.rs;

import com.libreria.model.Multa;
import com.libreria.dto.MultaDTO;
import com.libreria.service.MultaService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Path("/multas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MultaResource {

    @Inject // 🎯 Inyección del servicio (CDI)
    private MultaService service;

    @GET
    public Response listar() {
        List<Multa> entities = service.findAll();
        // Mapear Entidades a DTOs
        List<MultaDTO> dtos = entities.stream()
                .map(MultaDTO::new)
                .collect(Collectors.toList());
        return Response.ok(dtos).build();
    }

    @GET
    @Path("{id}")
    public Response obtener(@PathParam("id") Integer id) {
        Multa m = service.find(id);
        if (m == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // Retornar el DTO
        return Response.ok(new MultaDTO(m)).build();
    }

    @POST
    public Response crear(MultaDTO multaDTO, @Context UriInfo uriInfo) {

        // 1. Convertir DTO a Entidad (las relaciones son nulas)
        Multa multa = multaDTO.toEntity();

        try {
            // 2. Llamar al servicio, pasando los IDs de las relaciones
            Multa creado = service.create(multa, multaDTO.getUsuarioId(), multaDTO.getPrestamoId());

            URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(creado.getId())).build();
            // 3. Retornar el DTO del objeto creado
            return Response.created(uri).entity(new MultaDTO(creado)).build();
        } catch (RuntimeException e) {
            // Manejo de error para Usuario/Prestamo no encontrado o validación
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("{id}")
    public Response actualizar(@PathParam("id") Integer id, MultaDTO cambiosDTO) {

        // 1. Convertir DTO a Entidad
        Multa cambios = cambiosDTO.toEntity();

        try {
            // 2. Llamar al servicio, pasando los IDs para actualizar las relaciones
            Multa actualizado = service.update(id, cambios, cambiosDTO.getUsuarioId(), cambiosDTO.getPrestamoId());

            if (actualizado == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            // 3. Retornar el DTO
            return Response.ok(new MultaDTO(actualizado)).build();
        } catch (RuntimeException e) {
            // Manejo de error para Usuario/Prestamo no encontrado
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