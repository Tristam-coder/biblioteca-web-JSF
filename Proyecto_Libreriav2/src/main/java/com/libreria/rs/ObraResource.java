package com.libreria.rs;

import com.libreria.model.Obra;
import com.libreria.dto.ObraDTO; // Importar DTO
import com.libreria.service.ObraService;
import jakarta.inject.Inject; // Importar inyección
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Path("/obras")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ObraResource {

    @Inject // 🎯 Usar inyección CDI/EJB
    private ObraService service;

    @GET
    public Response listar() {
        List<Obra> all = service.findAll();
        // Mapear Entidades a DTOs
        List<ObraDTO> dtos = all.stream()
                .map(ObraDTO::new)
                .collect(Collectors.toList());
        return Response.ok(dtos).build();
    }

    @GET
    @Path("{id}")
    public Response obtener(@PathParam("id") Integer id) {
        Obra o = service.find(id);
        if (o == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // Retornar el DTO
        return Response.ok(new ObraDTO(o)).build();
    }

    @POST
    public Response crear(ObraDTO obraDTO, @Context UriInfo uriInfo) {
        Obra obra = obraDTO.toEntity();

        try {
            // Llamar al servicio, pasando los IDs de las relaciones
            Obra creado = service.create(obra, obraDTO.getTipoObraId(), obraDTO.getEditorialId());

            URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(creado.getId())).build();
            // Retornar el DTO del objeto creado
            return Response.created(uri).entity(new ObraDTO(creado)).build();
        } catch (RuntimeException e) {
            // Manejo de errores para entidades relacionadas no encontradas
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("{id}")
    public Response actualizar(@PathParam("id") Integer id, ObraDTO cambiosDTO) {
        Obra cambios = cambiosDTO.toEntity();

        try {
            // Llamar al servicio, pasando los IDs para actualizar las relaciones
            Obra actualizado = service.update(id, cambios, cambiosDTO.getTipoObraId(), cambiosDTO.getEditorialId());

            if (actualizado == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            // Retornar el DTO
            return Response.ok(new ObraDTO(actualizado)).build();
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