package beans;

import java.util.List;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import dao.AdministradorDAO;
import dao.Conexion;
import dao.PedidoDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import modelo.Pedido;
import modelo.Producto;
import modelo.Usuario;
import modelo.Administrador;

@ManagedBean(name="adminBean")
@SessionScoped
public class AdminBean implements Serializable {
    private static final long serialVersionUID = 1L;

    // --- Administrador ---
    private Administrador administrador = new Administrador();   
    private Administrador administradorLogueado;                
    private String contrasenaConfirmacion;   

    // --- Listas ---
    private List<Usuario> usuarios;
    private List<Pedido> ultimosPedidos;
    private List<Producto> inventario;

    // --- Estadísticas ---
    private int ventasHoy;
    private int productosVendidosHoy;
    private int clientesNuevosHoy;

    // ================== CICLO DE VIDA ==================
    @PostConstruct
    public void init() {
        usuarios = cargarUsuarios();
        // Aquí también podrías inicializar inventario y pedidos si tienes DAOs
        System.out.println("Usuarios cargados: " + (usuarios != null ? usuarios.size() : 0));
    }
    
    

    // ================== GETTERS & SETTERS ==================
    public Administrador getAdministrador() { return administrador; }
    public void setAdministrador(Administrador administrador) { this.administrador = administrador; }

    public Administrador getAdministradorLogueado() { return administradorLogueado; }
    public void setAdministradorLogueado(Administrador administradorLogueado) { this.administradorLogueado = administradorLogueado; }

    public String getContrasenaConfirmacion() { return contrasenaConfirmacion; }
    public void setContrasenaConfirmacion(String contrasenaConfirmacion) { this.contrasenaConfirmacion = contrasenaConfirmacion; }

    public List<Usuario> getUsuarios() {
        if (usuarios == null || usuarios.isEmpty()) {
            usuarios = cargarUsuarios();
        }
        return usuarios;
    }
    public void setUsuarios(List<Usuario> usuarios) { this.usuarios = usuarios; }

    public int getVentasHoy() { return ventasHoy; }
    public void setVentasHoy(int ventasHoy) { this.ventasHoy = ventasHoy; }

    public int getProductosVendidosHoy() { return productosVendidosHoy; }
    public void setProductosVendidosHoy(int productosVendidosHoy) { this.productosVendidosHoy = productosVendidosHoy; }

    public int getClientesNuevosHoy() { return clientesNuevosHoy; }
    public void setClientesNuevosHoy(int clientesNuevosHoy) { this.clientesNuevosHoy = clientesNuevosHoy; }

    public List<Pedido> getUltimosPedidos() { return ultimosPedidos; }
    public void setUltimosPedidos(List<Pedido> ultimosPedidos) { this.ultimosPedidos = ultimosPedidos; }

    public List<Producto> getInventario() { return inventario; }
    public void setInventario(List<Producto> inventario) { this.inventario = inventario; }

    // ================== MÉTODOS ==================

    // --- REGISTRAR ---
    public String registrar() {
        if (!administrador.getContraseña().equals(contrasenaConfirmacion)) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Las contraseñas no coinciden"));
            return null;
        }
        try {
            AdministradorDAO dao = new AdministradorDAO();
            boolean guardado = dao.registrar(administrador);

            if (guardado) {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Administrador registrado correctamente"));

                administrador = new Administrador();
                contrasenaConfirmacion = null;

                return "/login.xhtml?faces-redirect=true";
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo registrar en la base de datos"));
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Ocurrió un problema en el registro"));
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
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Usuario o contraseña incorrectos"));
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Ocurrió un problema al iniciar sesión"));
            return null;
        }
    }

    // --- CERRAR SESIÓN ---
    public String cerrarSesion() {
        administradorLogueado = null;
        return "/login.xhtml?faces-redirect=true";
    }

    // --- CARGAR USUARIOS ---
    private List<Usuario> cargarUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(
                 "SELECT pk_idusuario, nombre, usuCorreo, rol FROM usuario")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("pk_idusuario"));
                u.setNombre(rs.getString("nombre"));
                u.setUsuCorreo(rs.getString("usuCorreo"));
                u.setRol(rs.getString("rol"));
                lista.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    public void eliminarUsuario(Usuario usuario) {
    try (Connection con = Conexion.conectar();
         PreparedStatement ps = con.prepareStatement(
             "DELETE FROM usuario WHERE pk_idusuario=?")) {
        ps.setInt(1, usuario.getId());
        int filas = ps.executeUpdate();

        if (filas > 0) {
            // Actualizar la lista en memoria
            usuarios.remove(usuario);
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                                 "Éxito", "Usuario eliminado correctamente"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                 "Aviso", "No se encontró el usuario"));
        }
    } catch (Exception e) {
        e.printStackTrace();
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_FATAL,
                             "Error", "Ocurrió un problema al eliminar"));
    }
}
private List<Integer> ventasSemana;

public List<Integer> getVentasSemana() {
    if (ventasSemana == null || ventasSemana.isEmpty()) {
        ventasSemana = cargarVentasSemana();
    }
    return ventasSemana;
}

public String getVentasSemanaJson() {
    return getVentasSemana().toString(); // devuelve "[1000,2000,3000]"
}

private List<Integer> cargarVentasSemana() {
    List<Integer> lista = new ArrayList<>();
    try (Connection con = Conexion.conectar();
         PreparedStatement ps = con.prepareStatement(
             "SELECT SUM(total) as totalDia " +
             "FROM ventas " +
             "WHERE WEEK(fecha) = WEEK(CURDATE()) " +
             "GROUP BY DAYOFWEEK(fecha) " +
             "ORDER BY DAYOFWEEK(fecha)")) {
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            lista.add(rs.getInt("totalDia"));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return lista;
}
public void cargarPedidos() {
    PedidoDAO dao = new PedidoDAO();
    ultimosPedidos = dao.obtenerUltimosPedidos();
}

}
