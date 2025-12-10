package beans;

import java.util.List;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import dao.AdministradorDAO;
import modelo.Pedido;
import modelo.Producto;
import modelo.Usuario;
import modelo.Administrador;

@ManagedBean(name="adminBean")
@SessionScoped
public class AdminBean implements Serializable {
    private Administrador administrador = new Administrador();   
    private Administrador administradorLogueado;                
    private String contrasenaConfirmacion;                       

    // --- GETTERS & SETTERS ---
    public Administrador getAdministrador() { return administrador; }
    public void setAdministrador(Administrador administrador) { this.administrador = administrador; }

    public Administrador getAdministradorLogueado() { return administradorLogueado; }
    public void setAdministradorLogueado(Administrador administradorLogueado) { this.administradorLogueado = administradorLogueado; }

    public String getContrasenaConfirmacion() { return contrasenaConfirmacion; }
    public void setContrasenaConfirmacion(String contrasenaConfirmacion) { this.contrasenaConfirmacion = contrasenaConfirmacion; }

    // --- REGISTRAR ---
    public String registrar() {
        if (!administrador.getContraseña().equals(contrasenaConfirmacion)) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                                 "Error", "Las contraseñas no coinciden"));
            return null;
        }
        try {
            AdministradorDAO dao = new AdministradorDAO();
            boolean guardado = dao.registrar(administrador);

            if (guardado) {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, 
                                     "Éxito", "Administrador registrado correctamente"));

                administrador = new Administrador();
                contrasenaConfirmacion = null;

                return "/login.xhtml?faces-redirect=true";
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                                     "Error", "No se pudo registrar en la base de datos"));
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_FATAL, 
                                 "Error", "Ocurrió un problema en el registro"));
            return null;
        }
    }

    // --- LOGIN ---
    public String iniciarSesion() {
        try {
            AdministradorDAO dao = new AdministradorDAO();
            administradorLogueado = dao.login(administrador.getUsuario(), administrador.getContraseña());

            if (administradorLogueado != null) {
                return "/admin/dashboard.xhtml?faces-redirect=true";
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                                     "Error", "Usuario o contraseña incorrectos"));
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_FATAL, 
                                 "Error", "Ocurrió un problema al iniciar sesión"));
            return null;
        }
    }

    // --- CERRAR SESIÓN ---
    public String cerrarSesion() {
        administradorLogueado = null;
        return "/login.xhtml?faces-redirect=true";
    }
    
    private int ventasHoy;

public int getVentasHoy() {
    return ventasHoy;
}

public void setVentasHoy(int ventasHoy) {
    this.ventasHoy = ventasHoy;
}

private int productosVendidosHoy;

public int getProductosVendidosHoy() {
    return productosVendidosHoy;
}

public void setProductosVendidosHoy(int productosVendidosHoy) {
    this.productosVendidosHoy = productosVendidosHoy;
}

private int clientesNuevosHoy;

public int getClientesNuevosHoy() {
    return clientesNuevosHoy;
}

public void setClientesNuevosHoy(int clientesNuevosHoy) {
    this.clientesNuevosHoy = clientesNuevosHoy;
}

private List<Pedido> ultimosPedidos;

public List<Pedido> getUltimosPedidos() {
    return ultimosPedidos;
}

public void setUltimosPedidos(List<Pedido> ultimosPedidos) {
    this.ultimosPedidos = ultimosPedidos;
}

private List<Producto> inventario;

public List<Producto> getInventario() {
    return inventario;
}

public void setInventario(List<Producto> inventario) {
    this.inventario = inventario;
}

private List<Usuario> usuarios;

public List<Usuario> getUsuarios() {
    return usuarios;
}

public void setUsuarios(List<Usuario> usuarios) {
    this.usuarios = usuarios;
}
    
}

