/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biblioteca.frontend.controller;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
/**
 *
 * @author Bryan
 */
@Named(value = "authController")
@SessionScoped
public class AuthController implements Serializable {

    @Inject
    private SessionController sessionController;
    
    private String username;
    private String password;
    private boolean recordar = false;

    public String login() {
        // Simulación de autenticación mejorada
        if ("admin".equals(username) && "admin".equals(password)) {
            sessionController.login("Administrador", true, 1L);
            return "/gestion-usuarios.xhtml?faces-redirect=true";
        } else if ("usuario".equals(username) && "123".equals(password)) {
            sessionController.login("Usuario Demo", false, 2L);
            return "/panel-usuario.xhtml?faces-redirect=true";
        } else {
            return "/login.xhtml?faces-redirect=true&error=true";
        }
    }

    public String logout() {
        return sessionController.logout();
    }

    // Getters y Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public boolean isRecordar() { return recordar; }
    public void setRecordar(boolean recordar) { this.recordar = recordar; }
    public boolean getRecordar() { return recordar; }
}