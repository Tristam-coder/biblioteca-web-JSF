package com.libreria.rs;

import com.libreria.dto.PrestamoDTO; // Importar DTO
import com.libreria.model.Prestamo;
import com.libreria.service.PrestamoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Path("/prestamos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PrestamoResource {

    @Inject // 🎯 Usar inyección
    private PrestamoService service;

    @GET
    public Response listar() {
        List<Prestamo> all = service.findAll();
        List<PrestamoDTO> dtos = all.stream().map(PrestamoDTO::new).collect(Collectors.toList());
        return Response.ok(dtos).build();
    }

    @GET
    @Path("{id}")
    public Response obtener(@PathParam("id") Integer id) {
        Prestamo p = service.find(id);
        if (p == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(new PrestamoDTO(p)).build();
    }

    @POST
    public Response crear(PrestamoDTO prestamoDTO, @Context UriInfo uriInfo) {
        Prestamo prestamo = prestamoDTO.toEntity();

        try {
            Prestamo creado = service.create(prestamo, prestamoDTO.getUsuarioId(), prestamoDTO.getEjemplarId());
            URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(creado.getId())).build();
            return Response.created(uri).entity(new PrestamoDTO(creado)).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("{id}")
    public Response actualizar(@PathParam("id") Integer id, PrestamoDTO cambiosDTO) {
        Prestamo cambios = cambiosDTO.toEntity();

        try {
            Prestamo actualizado = service.update(id, cambios, cambiosDTO.getUsuarioId(), cambiosDTO.getEjemplarId());
            if (actualizado == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(new PrestamoDTO(actualizado)).build();
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