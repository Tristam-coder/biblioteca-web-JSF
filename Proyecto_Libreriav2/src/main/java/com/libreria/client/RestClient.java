package com.libreria.client;

import com.libreria.dto.*;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Cliente REST para consumir la API del backend.
 * Usa JAX-RS Client API para comunicarse con los endpoints REST.
 */
@ApplicationScoped
public class RestClient implements Serializable {

    private static final Logger LOG = Logger.getLogger(RestClient.class.getName());

    // URL base de la API REST
    private static final String BASE_URL = "http://localhost:8080/Proyecto_Libreria-1.0-SNAPSHOT/api";

    private Client client;

    @PostConstruct
    public void init() {
        this.client = ClientBuilder.newClient();
        LOG.info("RestClient inicializado con URL base: " + BASE_URL);
    }

    // ========== MÉTODOS GENÉRICOS ==========

    /**
     * GET - Obtener lista de recursos
     */
    public <T> List<T> getList(String path, Class<T[]> responseType) {
        try {
            WebTarget target = client.target(BASE_URL).path(path);
            Response response = target.request(MediaType.APPLICATION_JSON).get();

            if (response.getStatus() == 200) {
                T[] array = response.readEntity(responseType);
                return Arrays.asList(array);
            } else {
                LOG.warning("GET " + path + " retornó código: " + response.getStatus());
                return Arrays.asList();
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error en GET " + path, e);
            return Arrays.asList();
        }
    }

    /**
     * GET - Obtener un recurso por ID
     */
    public <T> T getById(String path, Integer id, Class<T> responseType) {
        try {
            WebTarget target = client.target(BASE_URL).path(path).path(String.valueOf(id));
            Response response = target.request(MediaType.APPLICATION_JSON).get();

            if (response.getStatus() == 200) {
                return response.readEntity(responseType);
            } else {
                LOG.warning("GET " + path + "/" + id + " retornó código: " + response.getStatus());
                return null;
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error en GET " + path + "/" + id, e);
            return null;
        }
    }

    /**
     * POST - Crear nuevo recurso
     */
    public <T> T post(String path, Object entity, Class<T> responseType) {
        try {
            WebTarget target = client.target(BASE_URL).path(path);
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .post(Entity.json(entity));

            if (response.getStatus() == 201 || response.getStatus() == 200) {
                return response.readEntity(responseType);
            } else {
                String error = response.readEntity(String.class);
                LOG.warning("POST " + path + " retornó código: " + response.getStatus() + " - " + error);
                return null;
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error en POST " + path, e);
            return null;
        }
    }

    /**
     * PUT - Actualizar recurso
     */
    public <T> T put(String path, Integer id, Object entity, Class<T> responseType) {
        try {
            WebTarget target = client.target(BASE_URL).path(path).path(String.valueOf(id));
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .put(Entity.json(entity));

            if (response.getStatus() == 200) {
                return response.readEntity(responseType);
            } else {
                String error = response.readEntity(String.class);
                LOG.warning("PUT " + path + "/" + id + " retornó código: " + response.getStatus() + " - " + error);
                return null;
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error en PUT " + path + "/" + id, e);
            return null;
        }
    }

    /**
     * DELETE - Eliminar recurso
     */
    public boolean delete(String path, Integer id) {
        try {
            WebTarget target = client.target(BASE_URL).path(path).path(String.valueOf(id));
            Response response = target.request(MediaType.APPLICATION_JSON).delete();

            if (response.getStatus() == 204 || response.getStatus() == 200) {
                return true;
            } else {
                LOG.warning("DELETE " + path + "/" + id + " retornó código: " + response.getStatus());
                return false;
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error en DELETE " + path + "/" + id, e);
            return false;
        }
    }

    // ========== MÉTODOS ESPECÍFICOS POR RECURSO ==========

    // USUARIOS
    public List<UsuarioDTO> getUsuarios() {
        return getList("usuarios", UsuarioDTO[].class);
    }

    public UsuarioDTO getUsuario(Integer id) {
        return getById("usuarios", id, UsuarioDTO.class);
    }

    public UsuarioDTO createUsuario(UsuarioDTO usuario) {
        return post("usuarios", usuario, UsuarioDTO.class);
    }

    public UsuarioDTO updateUsuario(Integer id, UsuarioDTO usuario) {
        return put("usuarios", id, usuario, UsuarioDTO.class);
    }

    public boolean deleteUsuario(Integer id) {
        return delete("usuarios", id);
    }

    // OBRAS
    public List<ObraDTO> getObras() {
        return getList("obras", ObraDTO[].class);
    }

    public ObraDTO getObra(Integer id) {
        return getById("obras", id, ObraDTO.class);
    }

    public ObraDTO createObra(ObraDTO obra) {
        return post("obras", obra, ObraDTO.class);
    }

    public ObraDTO updateObra(Integer id, ObraDTO obra) {
        return put("obras", id, obra, ObraDTO.class);
    }

    public boolean deleteObra(Integer id) {
        return delete("obras", id);
    }

    // PRÉSTAMOS
    public List<PrestamoDTO> getPrestamos() {
        return getList("prestamos", PrestamoDTO[].class);
    }

    public PrestamoDTO getPrestamo(Integer id) {
        return getById("prestamos", id, PrestamoDTO.class);
    }

    public PrestamoDTO createPrestamo(PrestamoDTO prestamo) {
        return post("prestamos", prestamo, PrestamoDTO.class);
    }

    public PrestamoDTO updatePrestamo(Integer id, PrestamoDTO prestamo) {
        return put("prestamos", id, prestamo, PrestamoDTO.class);
    }

    public boolean deletePrestamo(Integer id) {
        return delete("prestamos", id);
    }

    // RESERVAS
    public List<ReservaDTO> getReservas() {
        return getList("reservas", ReservaDTO[].class);
    }

    public ReservaDTO getReserva(Integer id) {
        return getById("reservas", id, ReservaDTO.class);
    }

    public ReservaDTO createReserva(ReservaDTO reserva) {
        return post("reservas", reserva, ReservaDTO.class);
    }

    public ReservaDTO updateReserva(Integer id, ReservaDTO reserva) {
        return put("reservas", id, reserva, ReservaDTO.class);
    }

    public boolean deleteReserva(Integer id) {
        return delete("reservas", id);
    }

    // AUTORES
    public List<AutorDTO> getAutores() {
        return getList("autores", AutorDTO[].class);
    }

    public AutorDTO getAutor(Integer id) {
        return getById("autores", id, AutorDTO.class);
    }

    public AutorDTO createAutor(AutorDTO autor) {
        return post("autores", autor, AutorDTO.class);
    }

    public AutorDTO updateAutor(Integer id, AutorDTO autor) {
        return put("autores", id, autor, AutorDTO.class);
    }

    public boolean deleteAutor(Integer id) {
        return delete("autores", id);
    }

    // EDITORIALES
    public List<EditorialDTO> getEditoriales() {
        return getList("editoriales", EditorialDTO[].class);
    }

    public EditorialDTO getEditorial(Integer id) {
        return getById("editoriales", id, EditorialDTO.class);
    }

    public EditorialDTO createEditorial(EditorialDTO editorial) {
        return post("editoriales", editorial, EditorialDTO.class);
    }

    public EditorialDTO updateEditorial(Integer id, EditorialDTO editorial) {
        return put("editoriales", id, editorial, EditorialDTO.class);
    }

    public boolean deleteEditorial(Integer id) {
        return delete("editoriales", id);
    }

    // EJEMPLARES
    public List<EjemplarDTO> getEjemplares() {
        return getList("ejemplares", EjemplarDTO[].class);
    }

    public EjemplarDTO getEjemplar(Integer id) {
        return getById("ejemplares", id, EjemplarDTO.class);
    }

    public EjemplarDTO createEjemplar(EjemplarDTO ejemplar) {
        return post("ejemplares", ejemplar, EjemplarDTO.class);
    }

    public EjemplarDTO updateEjemplar(Integer id, EjemplarDTO ejemplar) {
        return put("ejemplares", id, ejemplar, EjemplarDTO.class);
    }

    public boolean deleteEjemplar(Integer id) {
        return delete("ejemplares", id);
    }

    // MULTAS
    public List<MultaDTO> getMultas() {
        return getList("multas", MultaDTO[].class);
    }

    public MultaDTO getMulta(Integer id) {
        return getById("multas", id, MultaDTO.class);
    }

    public MultaDTO createMulta(MultaDTO multa) {
        return post("multas", multa, MultaDTO.class);
    }

    public MultaDTO updateMulta(Integer id, MultaDTO multa) {
        return put("multas", id, multa, MultaDTO.class);
    }

    public boolean deleteMulta(Integer id) {
        return delete("multas", id);
    }

    // TIPOS DE OBRA
    public List<TipoObraDTO> getTiposObra() {
        return getList("tipoobras", TipoObraDTO[].class);
    }

    public TipoObraDTO getTipoObra(Integer id) {
        return getById("tipoobras", id, TipoObraDTO.class);
    }

    public TipoObraDTO createTipoObra(TipoObraDTO tipoObra) {
        return post("tipoobras", tipoObra, TipoObraDTO.class);
    }

    public TipoObraDTO updateTipoObra(Integer id, TipoObraDTO tipoObra) {
        return put("tipoobras", id, tipoObra, TipoObraDTO.class);
    }

    public boolean deleteTipoObra(Integer id) {
        return delete("tipoobras", id);
    }

    // NOTIFICACIONES
    public List<NotificacionDTO> getNotificaciones() {
        return getList("notificaciones", NotificacionDTO[].class);
    }

    public NotificacionDTO getNotificacion(Integer id) {
        return getById("notificaciones", id, NotificacionDTO.class);
    }

    public NotificacionDTO createNotificacion(NotificacionDTO notificacion) {
        return post("notificaciones", notificacion, NotificacionDTO.class);
    }

    public NotificacionDTO updateNotificacion(Integer id, NotificacionDTO notificacion) {
        return put("notificaciones", id, notificacion, NotificacionDTO.class);
    }

    public boolean deleteNotificacion(Integer id) {
        return delete("notificaciones", id);
    }

    // HISTORIAL
    public List<HistorialDTO> getHistoriales() {
        return getList("historiales", HistorialDTO[].class);
    }

    public HistorialDTO getHistorial(Integer id) {
        return getById("historiales", id, HistorialDTO.class);
    }

    public HistorialDTO createHistorial(HistorialDTO historial) {
        return post("historiales", historial, HistorialDTO.class);
    }

    public HistorialDTO updateHistorial(Integer id, HistorialDTO historial) {
        return put("historiales", id, historial, HistorialDTO.class);
    }

    public boolean deleteHistorial(Integer id) {
        return delete("historiales", id);
    }
}

