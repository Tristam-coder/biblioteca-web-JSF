/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biblioteca.frontend.controller;

import biblioteca.frontend.model.BookDTO;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
/**
 *
 * @author Bryan
 */
@Named
@ViewScoped
public class CatalogoController implements Serializable {

    private String searchTerm;
    private List<BookDTO> libros;
    private List<BookDTO> librosFiltrados;
    private BookDTO libroSeleccionado;
    private String filtroIdioma = "todos";
    private String filtroDisponibilidad = "todos";

    public CatalogoController() {
        libros = new ArrayList<>();
        librosFiltrados = new ArrayList<>();
        cargarDatosPrueba();
    }

    private void cargarDatosPrueba() {
        libros.clear();
        libros.add(new BookDTO("978-8420471830", "Cien Años de Soledad", "Gabriel García Márquez", "Español", 
            "Una obra maestra del realismo mágico que narra la historia de la familia Buendía en Macondo."));
        libros.add(new BookDTO("978-0743273565", "El Gran Gatsby", "F. Scott Fitzgerald", "Inglés", 
            "Clásico de la literatura americana sobre la década de 1920 y el sueño americano."));
        libros.add(new BookDTO("978-8437604947", "1984", "George Orwell", "Español", 
            "Novela distópica sobre un futuro totalitario y la vigilancia masiva."));
        libros.add(new BookDTO("978-8497593798", "Don Quijote de la Mancha", "Miguel de Cervantes", "Español", 
            "Obra cumbre de la literatura española sobre las aventuras de un hidalgo enloquecido."));
        libros.add(new BookDTO("978-8437604948", "Rayuela", "Julio Cortázar", "Español", 
            "Novela innovadora que puede leerse de múltiples formas, rompiendo con la estructura lineal tradicional."));
        libros.add(new BookDTO("978-8437604949", "La Casa de los Espíritus", "Isabel Allende", "Español", 
            "Novela que combina realismo mágico con la historia de una familia latinoamericana a lo largo del siglo XX."));
        libros.add(new BookDTO("978-8437604950", "El Túnel", "Ernesto Sábato", "Español", 
            "Novela psicológica que explora la obsesión y la soledad existencial."));
        libros.add(new BookDTO("978-8437604951", "Pedro Páramo", "Juan Rulfo", "Español", 
            "Obra maestra de la literatura mexicana que mezcla realidad y fantasía en el pueblo de Comala."));
        
        aplicarFiltros();
    }

    public void buscar() {
        aplicarFiltros();
    }

    public void limpiarBusqueda() {
        searchTerm = "";
        filtroIdioma = "todos";
        filtroDisponibilidad = "todos";
        cargarDatosPrueba();
    }

    private void aplicarFiltros() {
        librosFiltrados = libros.stream()
            .filter(libro -> 
                (searchTerm == null || searchTerm.isEmpty() || 
                 libro.getTitulo().toLowerCase().contains(searchTerm.toLowerCase()) ||
                 libro.getAutor().toLowerCase().contains(searchTerm.toLowerCase()) ||
                 libro.getIsbn().contains(searchTerm)) &&
                (filtroIdioma.equals("todos") || libro.getIdioma().equalsIgnoreCase(filtroIdioma)) &&
                (filtroDisponibilidad.equals("todos"))
            )
            .collect(Collectors.toList());
    }

    public String verDetalle(BookDTO libro) {
        this.libroSeleccionado = libro;
        return "/detalle-obra.xhtml?faces-redirect=true";
    }

    // Getters y Setters
    public String getSearchTerm() { return searchTerm; }
    public void setSearchTerm(String searchTerm) { this.searchTerm = searchTerm; }
    public List<BookDTO> getLibros() { return librosFiltrados.isEmpty() ? libros : librosFiltrados; }
    public BookDTO getLibroSeleccionado() { return libroSeleccionado; }
    public String getFiltroIdioma() { return filtroIdioma; }
    public void setFiltroIdioma(String filtroIdioma) { this.filtroIdioma = filtroIdioma; }
    public String getFiltroDisponibilidad() { return filtroDisponibilidad; }
    public void setFiltroDisponibilidad(String filtroDisponibilidad) { this.filtroDisponibilidad = filtroDisponibilidad; }
}