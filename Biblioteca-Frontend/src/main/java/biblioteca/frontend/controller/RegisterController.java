/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biblioteca.frontend.controller;

import biblioteca.frontend.model.UserDTO;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;
/**
 *
 * @author Bryan
 */
@Named
@ViewScoped
public class RegisterController implements Serializable {

    private UserDTO usuario;
    private String password;
    private String confirmPassword;
    private Boolean aceptaTerminos = false;

    public RegisterController() {
        usuario = new UserDTO();
    }

    public String registrar() {
        if (!password.equals(confirmPassword)) {
            return null;
        }
        
        if (!aceptaTerminos) {
            return null;
        }

        // Simular registro exitoso
        System.out.println("Registrando usuario: " + usuario.getEmail());
        
        return "/login.xhtml?faces-redirect=true&registro=exitoso";
    }

    // Getters y Setters
    public UserDTO getUsuario() { return usuario; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }
    public Boolean getAceptaTerminos() { return aceptaTerminos; }
    public void setAceptaTerminos(Boolean aceptaTerminos) { this.aceptaTerminos = aceptaTerminos; }
}