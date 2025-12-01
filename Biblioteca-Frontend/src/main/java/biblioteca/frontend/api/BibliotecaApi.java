/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biblioteca.frontend.api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
/**
 *
 * @author Bryan
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BibliotecaApi {
    
    @GET
    @Path("/estadisticas")
    public Response obtenerEstadisticas() {
        String estadisticas = "{" +
            "\"totalLibros\": 1500," +
            "\"librosDisponibles\": 1250," +
            "\"prestamosActivos\": 87," +
            "\"usuariosRegistrados\": 450," +
            "\"librosMasPopulares\": [" +
            "    {\"titulo\": \"Cien años de soledad\", \"prestamos\": 45}," +
            "    {\"titulo\": \"1984\", \"prestamos\": 38}," +
            "    {\"titulo\": \"Don Quijote\", \"prestamos\": 32}" +
            "]" +
            "}";
        
        return Response.ok(estadisticas).build();
    }
    
    @POST
    @Path("/buscar")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response buscarLibros(@FormParam("termino") String termino,
                                @FormParam("idioma") String idioma,
                                @FormParam("tema") String tema) {
        
        String respuesta = "{" +
            "\"resultados\": [" +
            "    {" +
            "        \"id\": 1," +
            "        \"titulo\": \"Cien años de soledad\"," +
            "        \"autor\": \"Gabriel García Márquez\"," +
            "        \"anio\": 1967," +
            "        \"idioma\": \"español\"," +
            "        \"disponible\": true" +
            "    }" +
            "]," +
            "\"total\": 1," +
            "\"filtrosAplicados\": {" +
            "    \"termino\": \"" + (termino != null ? termino : "") + "\"," +
            "    \"idioma\": \"" + (idioma != null ? idioma : "") + "\"," +
            "    \"tema\": \"" + (tema != null ? tema : "") + "\"" +
            "}" +
            "}";
        
        return Response.ok(respuesta).build();
    }
    
    @POST
    @Path("/exportar")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response exportarResultados(@FormParam("formato") String formato) {
        
        String respuesta = "{" +
            "\"estado\": \"success\"," +
            "\"mensaje\": \"Exportación completada\"," +
            "\"formato\": \"" + formato + "\"," +
            "\"url\": \"/biblioteca/exportaciones/catalogo." + formato + "\"" +
            "}";
        
        return Response.ok(respuesta).build();
    }
    
    @GET
    @Path("/libro/{id}")
    public Response obtenerLibro(@PathParam("id") int id) {
        
        String libro = "{" +
            "\"id\": " + id + "," +
            "\"titulo\": \"Cien años de soledad\"," +
            "\"autor\": \"Gabriel García Márquez\"," +
            "\"anio\": 1967," +
            "\"idioma\": \"español\"," +
            "\"isbn\": \"978-8437604947\"," +
            "\"disponible\": true," +
            "\"descripcion\": \"Una obra maestra del realismo mágico\"" +
            "}";
        
        return Response.ok(libro).build();
    }
    
    @POST
    @Path("/auth/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response login(@FormParam("username") String username,
                         @FormParam("password") String password) {
        
        String respuesta = "{" +
            "\"success\": true," +
            "\"user\": {" +
            "    \"id\": 1," +
            "    \"name\": \"Usuario Demo\"," +
            "    \"email\": \"" + username + "\"," +
            "    \"admin\": false" +
            "}" +
            "}";
        
        return Response.ok(respuesta).build();
    }
}