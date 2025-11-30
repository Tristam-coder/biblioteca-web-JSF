// Archivo: com.libreria.rs.UsuarioResource.java

package com.libreria.rs;

import com.libreria.model.Usuario; // Todavía necesitamos la entidad para la capa de servicio
import com.libreria.dto.UsuarioDTO; // Importamos el DTO
import com.libreria.service.UsuarioService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors; // Para mapear listas

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    private UsuarioService service = new UsuarioService();

    @GET
    public Response listar() {
        List<Usuario> entities = service.findAll();
        // 1. Mapeamos la lista de Entidades a una lista de DTOs antes de retornar
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
        // 2. Retornamos el DTO
        return Response.ok(new UsuarioDTO(u)).build();
    }

    @POST
    // Ahora consume un DTO para crear (incluye nombre, email, password SIN ID/CREATED_AT)
    public Response crear(UsuarioDTO usuarioDTO, @Context UriInfo uriInfo) {
        // En un caso real, necesitarías un DTO de creación (UsuarioCreateDTO) que incluya la password.
        // Aquí asumimos que el UsuarioDTO de entrada tiene los campos necesarios para crear la entidad.
        Usuario nuevaEntidad = usuarioDTO.toEntity();

        // ¡IMPORTANTE! El DTO no incluye la password. Debes enviarla en el POST body y mapearla.
        // ASUMIENDO que el UsuarioDTO de entrada *si* tiene la password, o creas un DTO específico para POST.

        // **Falta lógica de mapeo de Password**
        // Por la estructura de tu DTO, el POST/PUT deberían recibir un DTO que *sí* tenga la password
        // y el service se encargaría de hashearla antes de enviarla al DAO.

        // **Solución Rápida de Ejemplo (Incompleta sin DTO de Creación)**
        // Asumiendo que `usuarioDTO` tiene todos los campos de la entidad, incluyendo `password`.
        // Para ser RESTful, la entrada del POST debe ser una Entidad/DTO con la password.

        // --- Mejor Práctica: Crear un DTO que incluya password para la entrada ---

        // **Asumiendo que el cuerpo de la petición es la Entidad Usuario completa, pero esto es inseguro:**
        // Usuario creado = service.create(usuario);

        // **Volvamos al DTO (requiere que el DTO de entrada mapee la password):**
        // Usuario creado = service.create(nuevaEntidad);

        // Por la estructura actual, vamos a usar el DTO. Debes asegurarte que el DTO que envías en el POST
        // incluya la password para que el `service.create` la reciba.
        // Si tu DTO no tiene `password`, tu API no funcionará para la creación.

        // Usamos la entidad original del request por simplicidad, pero se recomienda usar un DTO de entrada.
        Usuario creado = service.create(nuevaEntidad);

        URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(creado.getId())).build();
        // 3. Retornamos el DTO del objeto creado
        return Response.created(uri).entity(new UsuarioDTO(creado)).build();
    }

    @PUT
    @Path("{id}")
    public Response actualizar(@PathParam("id") Integer id, UsuarioDTO cambiosDTO) {
        // Mapear DTO de cambios a una Entidad de cambios parcial (o usar un DTO de actualización)
        Usuario cambios = cambiosDTO.toEntity();

        Usuario actualizado = service.update(id, cambios);

        if (actualizado == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // 4. Retornamos el DTO
        return Response.ok(new UsuarioDTO(actualizado)).build();
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