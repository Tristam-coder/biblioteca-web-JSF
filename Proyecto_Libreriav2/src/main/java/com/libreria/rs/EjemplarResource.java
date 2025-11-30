package com.libreria.rs;

import com.libreria.model.Ejemplar;
import com.libreria.dto.EjemplarDTO; // Importar el DTO
import com.libreria.service.EjemplarService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Path("/ejemplares")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EjemplarResource {

    private EjemplarService service = new EjemplarService();

    @GET
    public Response listar() {
        List<Ejemplar> entities = service.findAll();
        // Mapear Entidades a DTOs
        List<EjemplarDTO> dtos = entities.stream()
                .map(EjemplarDTO::new)
                .collect(Collectors.toList());
        return Response.ok(dtos).build();
    }

    @GET
    @Path("{id}")
    public Response obtener(@PathParam("id") Integer id) {
        Ejemplar e = service.find(id);
        if (e == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // Retornar el DTO
        return Response.ok(new EjemplarDTO(e)).build();
    }

    @POST
    public Response crear(EjemplarDTO ejemplarDTO, @Context UriInfo uriInfo) {

        // 1. Convertir DTO a Entidad (la relación Obra aún es nula)
        Ejemplar ejemplar = ejemplarDTO.toEntity();

        // 2. Llamar al servicio, pasando el ID de la Obra por separado
        Ejemplar creado = service.create(ejemplar, ejemplarDTO.getObraId());

        URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(creado.getId())).build();
        // 3. Retornar el DTO del objeto creado
        return Response.created(uri).entity(new EjemplarDTO(creado)).build();
    }

    @PUT
    @Path("{id}")
    public Response actualizar(@PathParam("id") Integer id, EjemplarDTO cambiosDTO) {

        // 1. Convertir DTO a Entidad
        Ejemplar cambios = cambiosDTO.toEntity();

        // 2. Llamar al servicio, pasando el ID de la Obra para actualizar la relación
        Ejemplar actualizado = service.update(id, cambios, cambiosDTO.getObraId());

        if (actualizado == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // 3. Retornar el DTO
        return Response.ok(new EjemplarDTO(actualizado)).build();
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