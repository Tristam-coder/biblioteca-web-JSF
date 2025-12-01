package com.libreria.rs;

import com.libreria.model.ObraAutor;
import com.libreria.dto.ObraAutorDTO; // Importar DTO
import com.libreria.service.ObraAutorService;
import jakarta.inject.Inject; // Importar inyección
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Path("/obraautores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ObraAutorResource {

    @Inject // 🎯 Usar inyección CDI/EJB
    private ObraAutorService service;

    @GET
    public Response listar() {
        List<ObraAutor> all = service.findAll();
        // Mapear Entidades a DTOs
        List<ObraAutorDTO> dtos = all.stream()
                .map(ObraAutorDTO::new)
                .collect(Collectors.toList());
        return Response.ok(dtos).build();
    }

    @GET
    @Path("{id}")
    public Response obtener(@PathParam("id") Integer id) {
        ObraAutor oa = service.find(id);
        if (oa == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // Retornar el DTO
        return Response.ok(new ObraAutorDTO(oa)).build();
    }

    @POST
    public Response crear(ObraAutorDTO obraAutorDTO, @Context UriInfo uriInfo) {
        ObraAutor obraAutor = obraAutorDTO.toEntity();

        try {
            // Llamar al servicio, pasando los IDs de las relaciones
            ObraAutor creado = service.create(obraAutor, obraAutorDTO.getObraId(), obraAutorDTO.getAutorId());

            URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(creado.getId())).build();
            // Retornar el DTO del objeto creado
            return Response.created(uri).entity(new ObraAutorDTO(creado)).build();
        } catch (RuntimeException e) {
            // Manejo de errores para entidades relacionadas no encontradas
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("{id}")
    public Response actualizar(@PathParam("id") Integer id, ObraAutorDTO cambiosDTO) {
        ObraAutor cambios = cambiosDTO.toEntity();

        try {
            // Llamar al servicio, pasando los IDs para actualizar las relaciones
            ObraAutor actualizado = service.update(id, cambios, cambiosDTO.getObraId(), cambiosDTO.getAutorId());

            if (actualizado == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            // Retornar el DTO
            return Response.ok(new ObraAutorDTO(actualizado)).build();
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