/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biblioteca.frontend.controller;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
/**
 *
 * @author Bryan
 */
@Named
@ViewScoped
public class RecoveryController implements Serializable {

    private String email;
    private boolean instruccionesEnviadas = false;

    public String enviarInstrucciones() {
        if (email == null || email.isEmpty()) {
            return null;
        }

        // Simular envío de instrucciones
        System.out.println("Enviando instrucciones de recuperación a: " + email);
        instruccionesEnviadas = true;
        
        return "/login.xhtml?faces-redirect=true&recuperacion=enviada";
    }

    // Getters y Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public boolean isInstruccionesEnviadas() { return instruccionesEnviadas; }
}