/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biblioteca.frontend.controller;

import biblioteca.frontend.model.FineDTO;
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
public class PaymentController implements Serializable {

    private List<FineDTO> multas;
    private Double totalPago = 25.50;
    private String metodoPago;
    private String numeroTarjeta;
    private String fechaExpiracion;
    private String cvv;
    private String nombreTarjeta;

    public PaymentController() {
        multas = new ArrayList<>();
        // Datos de prueba
        FineDTO multa1 = new FineDTO();
        multa1.setConcepto("Retraso en devolución - Cien Años de Soledad");
        multa1.setMonto(15.00);
        multas.add(multa1);

        FineDTO multa2 = new FineDTO();
        multa2.setConcepto("Retraso en devolución - 1984");
        multa2.setMonto(10.50);
        multas.add(multa2);
    }

    public String procesarPago() {
        if (metodoPago == null || metodoPago.isEmpty()) {
            return null;
        }
        
        // Simular procesamiento exitoso
        System.out.println("Procesando pago con método: " + metodoPago);
        
        return "/panel-usuario.xhtml?faces-redirect=true&pago=exitoso";
    }

    // Getters y Setters
    public List<FineDTO> getMultas() { return multas; }
    public Double getTotalPago() { return totalPago; }
    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }
    public String getNumeroTarjeta() { return numeroTarjeta; }
    public void setNumeroTarjeta(String numeroTarjeta) { this.numeroTarjeta = numeroTarjeta; }
    public String getFechaExpiracion() { return fechaExpiracion; }
    public void setFechaExpiracion(String fechaExpiracion) { this.fechaExpiracion = fechaExpiracion; }
    public String getCvv() { return cvv; }
    public void setCvv(String cvv) { this.cvv = cvv; }
    public String getNombreTarjeta() { return nombreTarjeta; }
    public void setNombreTarjeta(String nombreTarjeta) { this.nombreTarjeta = nombreTarjeta; }
}