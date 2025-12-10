package beans;

import dao.LoginDAO;
import dao.UsuarioDAO;
import java.io.IOException;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import modelo.Usuario;

@ManagedBean(name="loginBean")
@SessionScoped
public class LoginBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String correo;
    private String clave;
    private Usuario usuarioLogueado;

    // --- GETTERS & SETTERS ---
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }

    public Usuario getUsuarioLogueado() { return usuarioLogueado; }

    // ------------------ INICIAR SESIÓN ------------------
    public String iniciarSesion() {
        try {
            LoginDAO loginDao = new LoginDAO();
            usuarioLogueado = loginDao.login(correo, clave);

            if (usuarioLogueado != null) {
                HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                        .getExternalContext().getSession(true);
                session.setAttribute("usuario", usuarioLogueado);

                // 🔹 Asignar el cliente al carrito
                if (carritoBean != null) {
                    carritoBean.setCliente(usuarioLogueado);
                }

                // Redirigir según rol
                if ("admin".equalsIgnoreCase(usuarioLogueado.getRol())) {
                    return "/admin?faces-redirect=true";
                } else if ("repartidor".equalsIgnoreCase(usuarioLogueado.getRol())) {
                    return "/repartidor?faces-redirect=true";
                } else {
                    return "/usuario?faces-redirect=true";
                }
            }

            // Si no encontró nada
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Datos incorrectos"));
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Problema al iniciar sesión"));
            return null;
        }
    }

    // ------------------ CERRAR SESIÓN ------------------
    public String cerrarSesion() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);

        if (session != null) session.invalidate();

        return "/login.xhtml?faces-redirect=true";
    }

    // ------------------ VERIFICAR SESIÓN ------------------
    public void verifSesionUsuario() {
        FacesContext context = FacesContext.getCurrentInstance();
        Usuario usuario = (Usuario) context.getExternalContext().getSessionMap().get("usuario");

        if (usuario == null || "admin".equalsIgnoreCase(usuario.getRol())) {
            try {
                context.getExternalContext().redirect(
                    context.getExternalContext().getRequestContextPath() + "/sinacceso.xhtml"
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void verifSesionAdmin() {
        FacesContext context = FacesContext.getCurrentInstance();
        Usuario usuario = (Usuario) context.getExternalContext().getSessionMap().get("usuario");

        if (usuario == null || !"admin".equalsIgnoreCase(usuario.getRol())) {
            try {
                context.getExternalContext().redirect(
                    context.getExternalContext().getRequestContextPath() + "/sinacceso.xhtml"
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void verifSesionRepartidor() {
        FacesContext context = FacesContext.getCurrentInstance();
        Usuario usuario = (Usuario) context.getExternalContext().getSessionMap().get("usuario");

        if (usuario == null || !"repartidor".equalsIgnoreCase(usuario.getRol())) {
            try {
                context.getExternalContext().redirect(
                    context.getExternalContext().getRequestContextPath() + "/sinacceso.xhtml"
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // ------------------ INTEGRACIÓN CON CARRITO ------------------
    @ManagedProperty(value="#{carritoBean}")
    private CarritoBean carritoBean;

    public CarritoBean getCarritoBean() {
        return carritoBean;
    }

    public void setCarritoBean(CarritoBean carritoBean) {
        this.carritoBean = carritoBean;
    }

    // ------------------ LOGIN USANDO UsuarioDAO ------------------
    public String login() {
        Usuario u = UsuarioDAO.validarUsuario(correo, clave);
        if (u != null) {
            usuarioLogueado = u;
            if (carritoBean != null) {
                carritoBean.setCliente(usuarioLogueado); // 🔹 asigna el cliente al carrito
            }
            return "home?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Credenciales inválidas"));
            return null;
        }
    }
}

