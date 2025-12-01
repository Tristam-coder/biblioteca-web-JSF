/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biblioteca.frontend.controller;

import biblioteca.frontend.model.BookDTO;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import javax.faces.context.FacesContext;
import java.text.ParseException;
/**
 *
 * @author Bryan
 */
@Named
@ViewScoped
public class LoanRequestController implements Serializable {

    private BookDTO libro;
    private Date fechaRetiro;
    private String fechaRetiroString; // Nuevo campo para manejar el String
    private String fechaDevolucionEstimada;
    private String puntoRetiro;
    private Boolean aceptaTerminos = false;

    public LoanRequestController() {
        libro = new BookDTO("978-8420471830", "Cien Años de Soledad", 
                           "Gabriel García Márquez", "Español", 
                           "Una obra maestra del realismo mágico que narra la historia de la familia Buendía en Macondo.");
        establecerFechaPorDefectoSegura();
    }

    private void establecerFechaPorDefectoSegura() {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            this.fechaRetiro = calendar.getTime();
            calcularFechaDevolucion();
        } catch (Exception e) {
            this.fechaRetiro = new Date();
            calcularFechaDevolucion();
        }
    }

    // Método para convertir String a Date cuando se recibe del formulario
    public void setFechaRetiroString(String fechaRetiroString) {
        this.fechaRetiroString = fechaRetiroString;
        if (fechaRetiroString != null && !fechaRetiroString.isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                this.fechaRetiro = sdf.parse(fechaRetiroString);
                calcularFechaDevolucion();
            } catch (ParseException e) {
                // Si falla, usar fecha por defecto
                establecerFechaPorDefectoSegura();
            }
        }
    }

    public String getFechaRetiroString() {
        if (fechaRetiro != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(fechaRetiro);
        }
        // Retornar fecha por defecto (mañana)
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(calendar.getTime());
    }

    public String siguientePaso() {
        // Si hay fechaString pero no se ha convertido a Date
        if (fechaRetiroString != null && !fechaRetiroString.isEmpty() && fechaRetiro == null) {
            setFechaRetiroString(fechaRetiroString);
        }
        
        if (!aceptaTerminos) {
            return null;
        }
        
        if (fechaRetiro == null) {
            establecerFechaPorDefectoSegura();
        }
        
        calcularFechaDevolucion();
        return "/confirmacion-prestamo.xhtml?faces-redirect=true";
    }

    private void calcularFechaDevolucion() {
        if (fechaRetiro != null) {
            try {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(fechaRetiro);
                calendar.add(Calendar.DAY_OF_YEAR, 15);
                
                Date fechaDevolucion = calendar.getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                fechaDevolucionEstimada = sdf.format(fechaDevolucion);
            } catch (Exception e) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_YEAR, 15);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                fechaDevolucionEstimada = sdf.format(calendar.getTime());
            }
        }
    }

    // Getters y Setters
    public BookDTO getLibro() { return libro; }
    public void setLibro(BookDTO libro) { this.libro = libro; }
    
    public Date getFechaRetiro() { 
        if (fechaRetiro == null) {
            establecerFechaPorDefectoSegura();
        }
        return fechaRetiro; 
    }
    
    public void setFechaRetiro(Date fechaRetiro) { 
        this.fechaRetiro = fechaRetiro;
        calcularFechaDevolucion();
    }
    
    public String getFechaDevolucionEstimada() { 
        if (fechaDevolucionEstimada == null) {
            calcularFechaDevolucion();
        }
        return fechaDevolucionEstimada; 
    }
    
    public String getPuntoRetiro() { return puntoRetiro; }
    public void setPuntoRetiro(String puntoRetiro) { this.puntoRetiro = puntoRetiro; }
    
    public Boolean getAceptaTerminos() { return aceptaTerminos; }
    public void setAceptaTerminos(Boolean aceptaTerminos) { this.aceptaTerminos = aceptaTerminos; }
}