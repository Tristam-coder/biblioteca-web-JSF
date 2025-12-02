package com.libreria.rs;

import com.libreria.model.Usuario;
import com.libreria.dto.UsuarioDTO;
import com.libreria.service.UsuarioService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject // 🎯 Inyección del servicio (CDI)
    private UsuarioService service;

    @GET
    public Response listar() {
        List<Usuario> entities = service.findAll();
        List<UsuarioDTO> dtos = entities.stream()
                .map(UsuarioDTO::new)
                .collect(Collectors.toList());
        return Response.ok(dtos).build();
    }

    @GET
    @Path("{id}")
    public Response obtener(@PathParam("id") Integer id) {
        Usuario u = service.find(id);
        if (u == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(new UsuarioDTO(u)).build();
    }

    @POST
    public Response crear(UsuarioDTO usuarioDTO, @Context UriInfo uriInfo) {
        Usuario nuevaEntidad = usuarioDTO.toEntity();

        try {
            Usuario creado = service.create(nuevaEntidad);

            URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(creado.getId())).build();
            return Response.created(uri).entity(new UsuarioDTO(creado)).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("{id}")
    public Response actualizar(@PathParam("id") Integer id, UsuarioDTO cambiosDTO) {
        Usuario cambios = cambiosDTO.toEntity();

        try {
            Usuario actualizado = service.update(id, cambios);

            if (actualizado == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(new UsuarioDTO(actualizado)).build();
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