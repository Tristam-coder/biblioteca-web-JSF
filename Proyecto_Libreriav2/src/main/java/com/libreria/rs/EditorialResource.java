package com.libreria.rs;

import com.libreria.model.Editorial;
import com.libreria.dto.EditorialDTO; // Importar el DTO
import com.libreria.service.EditorialService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Path("/editoriales")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EditorialResource {

    private EditorialService service = new EditorialService();

    @GET
    public Response listar() {
        List<Editorial> entities = service.findAll();
        // Mapear la lista de Entidades a una lista de DTOs para la respuesta
        List<EditorialDTO> dtos = entities.stream()
                .map(EditorialDTO::new)
                .collect(Collectors.toList());
        return Response.ok(dtos).build();
    }

    @GET
    @Path("{id}")
    public Response obtener(@PathParam("id") Integer id) {
        Editorial e = service.find(id);
        if (e == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // Retornar el DTO
        return Response.ok(new EditorialDTO(e)).build();
    }

    @POST
    public Response crear(EditorialDTO editorialDTO, @Context UriInfo uriInfo) {
        // Convertir DTO de entrada a Entidad para el servicio
        Editorial editorial = editorialDTO.toEntity();

        Editorial creado = service.create(editorial);

        URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(creado.getId())).build();
        // Retornar el DTO del objeto creado
        return Response.created(uri).entity(new EditorialDTO(creado)).build();
    }

    @PUT
    @Path("{id}")
    public Response actualizar(@PathParam("id") Integer id, EditorialDTO cambiosDTO) {
        // Convertir DTO de cambios a Entidad para el servicio
        Editorial cambios = cambiosDTO.toEntity();

        Editorial actualizado = service.update(id, cambios);

        if (actualizado == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // Retornar el DTO
        return Response.ok(new EditorialDTO(actualizado)).build();
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