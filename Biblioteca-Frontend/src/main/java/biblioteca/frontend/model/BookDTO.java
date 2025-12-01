/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biblioteca.frontend.model;

import java.io.Serializable;
/**
 *
 * @author Bryan
 */
public class BookDTO implements Serializable {
    private String isbn;
    private String titulo;
    private String autor;
    private String idioma;
    private String descripcion;

    public BookDTO() {}

    public BookDTO(String isbn, String titulo, String autor, String idioma, String descripcion) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.idioma = idioma;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }
    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}