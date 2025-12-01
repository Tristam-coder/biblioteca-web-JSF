/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biblioteca.frontend.controller;

import biblioteca.frontend.model.UserDTO;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Bryan
 */
@Named
@ViewScoped
public class AdminController implements Serializable {

    private List<UserDTO> listaUsuarios;
    private UserDTO usuarioSeleccionado;
    private String filtroBusqueda;

    public AdminController() {
        listaUsuarios = new ArrayList<>();
        cargarDatosPrueba();
    }

    private void cargarDatosPrueba() {
        listaUsuarios.add(new UserDTO("Juan", "Pérez", "juan@email.com"));
        listaUsuarios.add(new UserDTO("María", "Gómez", "maria@email.com"));
        listaUsuarios.add(new UserDTO("Carlos", "López", "carlos@email.com"));
        listaUsuarios.add(new UserDTO("Ana", "Rodríguez", "ana@email.com"));
        listaUsuarios.add(new UserDTO("Luis", "Martínez", "luis@email.com"));
    }

    public void buscarUsuarios() {
        if (filtroBusqueda == null || filtroBusqueda.trim().isEmpty()) {
            cargarDatosPrueba();
        } else {
            List<UserDTO> resultados = new ArrayList<>();
            for (UserDTO usuario : listaUsuarios) {
                if (usuario.getNombreCompleto().toLowerCase().contains(filtroBusqueda.toLowerCase()) ||
                    usuario.getEmail().toLowerCase().contains(filtroBusqueda.toLowerCase())) {
                    resultados.add(usuario);
                }
            }
            listaUsuarios = resultados;
        }
    }

    public void limpiarBusqueda() {
        filtroBusqueda = "";
        cargarDatosPrueba();
    }

    public void eliminarUsuario(UserDTO usuario) {
        listaUsuarios.remove(usuario);
    }

    // Getters y Setters
    public List<UserDTO> getListaUsuarios() { return listaUsuarios; }
    public UserDTO getUsuarioSeleccionado() { return usuarioSeleccionado; }
    public void setUsuarioSeleccionado(UserDTO usuarioSeleccionado) { this.usuarioSeleccionado = usuarioSeleccionado; }
    public String getFiltroBusqueda() { return filtroBusqueda; }
    public void setFiltroBusqueda(String filtroBusqueda) { this.filtroBusqueda = filtroBusqueda; }
}