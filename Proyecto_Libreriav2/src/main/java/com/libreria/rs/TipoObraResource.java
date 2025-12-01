package com.libreria.rs;

import com.libreria.model.TipoObra;
import com.libreria.dto.TipoObraDTO; // Importar DTO
import com.libreria.service.TipoObraService;
import jakarta.inject.Inject; // Importar inyección
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Path("/tiposobra")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TipoObraResource {

    @Inject // 🎯 Usar inyección CDI/EJB
    private TipoObraService service;

    @GET
    public Response listar() {
        List<TipoObra> all = service.findAll();
        // Mapear Entidades a DTOs
        List<TipoObraDTO> dtos = all.stream()
                .map(TipoObraDTO::new)
                .collect(Collectors.toList());
        return Response.ok(dtos).build();
    }

    @GET
    @Path("{id}")
    public Response obtener(@PathParam("id") Integer id) {
        TipoObra t = service.find(id);
        if (t == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // Retornar el DTO
        return Response.ok(new TipoObraDTO(t)).build();
    }

    @POST
    public Response crear(TipoObraDTO tipoObraDTO, @Context UriInfo uriInfo) {
        // Mapear DTO a Entidad
        TipoObra tipoObra = tipoObraDTO.toEntity();

        TipoObra creado = service.create(tipoObra);

        URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(creado.getId())).build();
        // Retornar el DTO del objeto creado
        return Response.created(uri).entity(new TipoObraDTO(creado)).build();
    }

    @PUT
    @Path("{id}")
    public Response actualizar(@PathParam("id") Integer id, TipoObraDTO cambiosDTO) {
        // Mapear DTO a Entidad
        TipoObra cambios = cambiosDTO.toEntity();

        TipoObra actualizado = service.update(id, cambios);

        if (actualizado == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // Retornar el DTO
        return Response.ok(new TipoObraDTO(actualizado)).build();
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