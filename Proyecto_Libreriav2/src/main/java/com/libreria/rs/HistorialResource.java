package com.libreria.rs;

import com.libreria.model.Historial;
import com.libreria.dto.HistorialDTO; // Importar el DTO
import com.libreria.service.HistorialService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Path("/historiales")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HistorialResource {

    private HistorialService service = new HistorialService();

    @GET
    public Response listar() {
        List<Historial> entities = service.findAll();
        // Mapear Entidades a DTOs
        List<HistorialDTO> dtos = entities.stream()
                .map(HistorialDTO::new)
                .collect(Collectors.toList());
        return Response.ok(dtos).build();
    }

    @GET
    @Path("{id}")
    public Response obtener(@PathParam("id") Integer id) {
        Historial h = service.find(id);
        if (h == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // Retornar el DTO
        return Response.ok(new HistorialDTO(h)).build();
    }

    @POST
    public Response crear(HistorialDTO historialDTO, @Context UriInfo uriInfo) {

        // 1. Convertir DTO a Entidad (la relación Usuario aún es nula)
        Historial historial = historialDTO.toEntity();

        // 2. Llamar al servicio, pasando el ID del Usuario por separado
        Historial creado = service.create(historial, historialDTO.getUsuarioId());

        URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(creado.getId())).build();
        // 3. Retornar el DTO del objeto creado
        return Response.created(uri).entity(new HistorialDTO(creado)).build();
    }

    @PUT
    @Path("{id}")
    public Response actualizar(@PathParam("id") Integer id, HistorialDTO cambiosDTO) {

        // 1. Convertir DTO a Entidad
        Historial cambios = cambiosDTO.toEntity();

        // 2. Llamar al servicio, pasando el ID del Usuario para actualizar la relación
        Historial actualizado = service.update(id, cambios, cambiosDTO.getUsuarioId());

        if (actualizado == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // 3. Retornar el DTO
        return Response.ok(new HistorialDTO(actualizado)).build();
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