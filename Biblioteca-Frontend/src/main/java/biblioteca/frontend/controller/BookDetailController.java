/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biblioteca.frontend.controller;

import biblioteca.frontend.model.BookDTO;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
/**
 *
 * @author Bryan
 */
@Named
@ViewScoped
public class BookDetailController implements Serializable {

    @Inject
    private CatalogoController catalogoController;
    
    private BookDTO libro;

    @PostConstruct
    public void init() {
        if (catalogoController != null && catalogoController.getLibroSeleccionado() != null) {
            this.libro = catalogoController.getLibroSeleccionado();
        } else {
            // Libro por defecto para demo
            this.libro = new BookDTO("978-8420471830", "Cien Años de Soledad", 
                                   "Gabriel García Márquez", "Español", 
                                   "La novela narra la historia de la familia Buendía a lo largo de siete generaciones " +
                                   "en el pueblo ficticio de Macondo. Considerada una obra maestra de la literatura " +
                                   "hispanoamericana y universal, es una de las obras más traducidas y leídas en español.");
        }
    }

    public String solicitarPrestamo() {
        if (sessionController.isAuthenticated()) {
            return "/solicitud-prestamo.xhtml?faces-redirect=true";
        } else {
            return "/login.xhtml?faces-redirect=true&redirectTo=solicitud";
        }
    }

    @Inject
    private SessionController sessionController;

    // Getters
    public BookDTO getLibro() { 
        if (libro == null) {
            init();
        }
        return libro; 
    }
}