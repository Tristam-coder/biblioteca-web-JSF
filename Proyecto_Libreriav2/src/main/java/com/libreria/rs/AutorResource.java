package com.libreria.rs;

import com.libreria.model.Autor;
import com.libreria.dto.AutorDTO; // Importar el DTO
import com.libreria.service.AutorService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors; // Para mapear listas

@Path("/autores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AutorResource {

    private AutorService service = new AutorService();

    @GET
    public Response listar() {
        List<Autor> entities = service.findAll();
        // Mapear la lista de Entidades a una lista de DTOs
        List<AutorDTO> dtos = entities.stream()
                .map(AutorDTO::new)
                .collect(Collectors.toList());
        return Response.ok(dtos).build();
    }

    @GET
    @Path("{id}")
    public Response obtener(@PathParam("id") Integer id) {
        Autor a = service.find(id);
        if (a == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // Retornar el DTO
        return Response.ok(new AutorDTO(a)).build();
    }

    @POST
    // Ahora consume un DTO
    public Response crear(AutorDTO autorDTO, @Context UriInfo uriInfo) {
        // Convertir DTO a Entidad antes de pasar al servicio
        Autor autor = autorDTO.toEntity();

        Autor creado = service.create(autor);

        URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(creado.getId())).build();
        // Retornar el DTO del objeto creado
        return Response.created(uri).entity(new AutorDTO(creado)).build();
    }

    @PUT
    @Path("{id}")
    // Ahora consume un DTO
    public Response actualizar(@PathParam("id") Integer id, AutorDTO cambiosDTO) {
        // Convertir DTO a Entidad antes de pasar al servicio
        Autor cambios = cambiosDTO.toEntity();

        Autor actualizado = service.update(id, cambios);

        if (actualizado == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // Retornar el DTO
        return Response.ok(new AutorDTO(actualizado)).build();
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