/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biblioteca.frontend.controller;

import biblioteca.frontend.model.LoanDTO;
import biblioteca.frontend.model.FineDTO;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 *
 * @author Bryan
 */
@Named
@ViewScoped
public class UserController implements Serializable {

    private List<LoanDTO> prestamosActivos;
    private List<LoanDTO> historialPrestamos;
    private List<FineDTO> multasActivas;
    private Double totalMultasPendientes = 25.50;

    public UserController() {
        cargarDatosPrueba();
    }

    private void cargarDatosPrueba() {
        // Préstamos activos
        prestamosActivos = new ArrayList<>();
        LoanDTO prestamo1 = new LoanDTO();
        prestamo1.setId(1L);
        prestamo1.setLibroTitulo("Cien Años de Soledad");
        prestamo1.setFechaPrestamo(new Date(System.currentTimeMillis() - 5 * 24 * 60 * 60 * 1000));
        prestamo1.setFechaDevolucion(new Date(System.currentTimeMillis() + 10 * 24 * 60 * 60 * 1000));
        prestamosActivos.add(prestamo1);

        LoanDTO prestamo2 = new LoanDTO();
        prestamo2.setId(2L);
        prestamo2.setLibroTitulo("1984");
        prestamo2.setFechaPrestamo(new Date(System.currentTimeMillis() - 2 * 24 * 60 * 60 * 1000));
        prestamo2.setFechaDevolucion(new Date(System.currentTimeMillis() + 13 * 24 * 60 * 60 * 1000));
        prestamosActivos.add(prestamo2);

        // Historial
        historialPrestamos = new ArrayList<>();
        LoanDTO historial1 = new LoanDTO();
        historial1.setLibroTitulo("El Gran Gatsby");
        historial1.setFechaPrestamo(new Date(System.currentTimeMillis() - 30 * 24 * 60 * 60 * 1000));
        historial1.setFechaDevolucionReal(new Date(System.currentTimeMillis() - 15 * 24 * 60 * 60 * 1000));
        historialPrestamos.add(historial1);

        // Multas
        multasActivas = new ArrayList<>();
        FineDTO multa1 = new FineDTO();
        multa1.setId(1L);
        multa1.setConcepto("Retraso en devolución - Cien Años de Soledad");
        multa1.setMonto(15.00);
        multa1.setFecha(new Date(System.currentTimeMillis() - 2 * 24 * 60 * 60 * 1000));
        multasActivas.add(multa1);

        FineDTO multa2 = new FineDTO();
        multa2.setId(2L);
        multa2.setConcepto("Retraso en devolución - 1984");
        multa2.setMonto(10.50);
        multa2.setFecha(new Date(System.currentTimeMillis() - 1 * 24 * 60 * 60 * 1000));
        multasActivas.add(multa2);
    }

    public String pagarMulta(FineDTO multa) {
        System.out.println("Redirigiendo a pago para multa: " + multa.getConcepto());
        return "/pago.xhtml?faces-redirect=true";
    }

    public String pagarTodasMultas() {
        System.out.println("Redirigiendo a pago para todas las multas");
        return "/pago.xhtml?faces-redirect=true";
    }

    public String renovarPrestamo(LoanDTO prestamo) {
        System.out.println("Renovando préstamo: " + prestamo.getLibroTitulo());
        // Lógica de renovación
        return null; // Permanece en la misma página
    }

    // Getters
    public List<LoanDTO> getPrestamosActivos() { return prestamosActivos; }
    public List<LoanDTO> getHistorialPrestamos() { return historialPrestamos; }
    public List<FineDTO> getMultasActivas() { return multasActivas; }
    public Double getTotalMultasPendientes() { return totalMultasPendientes; }
    public List<FineDTO> getReservasActivas() { return new ArrayList<>(); }
}