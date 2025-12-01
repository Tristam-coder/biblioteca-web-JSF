/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biblioteca.frontend.controller;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
/**
 *
 * @author Bryan
 */
@Named
@SessionScoped
public class SessionController implements Serializable {
    private boolean authenticated = false;
    private boolean admin = false;
    private String userName = "Invitado";
    private Long userId;
    
    public void login(String username, boolean isAdmin, Long userId) {
        this.authenticated = true;
        this.userName = username;
        this.admin = isAdmin;
        this.userId = userId;
    }
    
    public String logout() {
        this.authenticated = false;
        this.admin = false;
        this.userName = "Invitado";
        this.userId = null;
        return "/index.xhtml?faces-redirect=true";
    }
    
    // Getters y Setters
    public boolean isAuthenticated() { return authenticated; }
    public boolean getAuthenticated() { return authenticated; }
    public boolean isAdmin() { return admin; }
    public boolean getAdmin() { return admin; }
    public String getUserName() { return userName; }
    public Long getUserId() { return userId; }
    public void setAuthenticated(boolean authenticated) { this.authenticated = authenticated; }
    public void setAdmin(boolean admin) { this.admin = admin; }
    public void setUserName(String userName) { this.userName = userName; }
    public void setUserId(Long userId) { this.userId = userId; }
}