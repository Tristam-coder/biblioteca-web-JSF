/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biblioteca.frontend.model;

import java.io.Serializable;
import java.util.Date;
/**
 *
 * @author Bryan
 */
public class LoanDTO implements Serializable {
    private Long id;
    private String libroTitulo;
    private Date fechaPrestamo;
    private Date fechaDevolucion;
    private Date fechaDevolucionReal;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getLibroTitulo() { return libroTitulo; }
    public void setLibroTitulo(String libroTitulo) { this.libroTitulo = libroTitulo; }
    public Date getFechaPrestamo() { return fechaPrestamo; }
    public void setFechaPrestamo(Date fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; }
    public Date getFechaDevolucion() { return fechaDevolucion; }
    public void setFechaDevolucion(Date fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }
    public Date getFechaDevolucionReal() { return fechaDevolucionReal; }
    public void setFechaDevolucionReal(Date fechaDevolucionReal) { this.fechaDevolucionReal = fechaDevolucionReal; }
}