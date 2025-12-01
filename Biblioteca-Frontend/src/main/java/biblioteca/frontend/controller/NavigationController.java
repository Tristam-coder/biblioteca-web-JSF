/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biblioteca.frontend.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
/**
 *
 * @author Bryan
 */
@Named
@RequestScoped
public class NavigationController {
    
    public String goToCatalogo() { 
        return "/catalogo.xhtml?faces-redirect=true"; 
    }
    
    public String goToLogin() { 
        return "/login.xhtml?faces-redirect=true"; 
    }
    
    public String goToRegistro() { 
        return "/registro.xhtml?faces-redirect=true"; 
    }
    
    public String goToDetalleObra() { 
        return "/detalle-obra.xhtml?faces-redirect=true"; 
    }
    
    public String goToSolicitudPrestamo() { 
        return "/solicitud-prestamo.xhtml?faces-redirect=true"; 
    }
    
    public String goToPanelUsuario() { 
        return "/panel-usuario.xhtml?faces-redirect=true"; 
    }
    
    public String goToPago() { 
        return "/pago.xhtml?faces-redirect=true"; 
    }
    
    public String goToGestionUsuarios() { 
        return "/gestion-usuarios.xhtml?faces-redirect=true"; 
    }
    
    public String goToIndex() { 
        return "/index.xhtml?faces-redirect=true"; 
    }

    public String goToRecuperarContrasena() { 
        return "/recuperar-contrasena.xhtml?faces-redirect=true"; 
    }
}