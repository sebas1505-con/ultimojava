package beans;

import modelo.Usuario;
import dao.UsuarioDAO;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name="usuarioBean")
@ViewScoped
public class UsuarioBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Usuario usuario = new Usuario();
    private List<Usuario> lista;

    // GETTERS & SETTERS
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getLista() {
        try {
            UsuarioDAO dao = new UsuarioDAO();
            lista = dao.listar();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    // MÉTODOS CRUD
    public void guardar() {
        try {
            UsuarioDAO dao = new UsuarioDAO();
            if(dao.registrar(usuario)) {
                System.out.println("Usuario registrado correctamente");
            }
            usuario = new Usuario();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actualizar() {
        try {
            UsuarioDAO dao = new UsuarioDAO();
            if(dao.actualizar(usuario)) {
                System.out.println("Usuario actualizado correctamente");
            }
            usuario = new Usuario();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminar(int id) {
        try {
            UsuarioDAO dao = new UsuarioDAO();
            if(dao.eliminar(id)) {
                System.out.println("Usuario eliminado correctamente");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cargarUsuario(Usuario u) {
        this.usuario = u;
    }
}
