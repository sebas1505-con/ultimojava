package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelo.Usuario;

public class UsuarioDAO {

    private Connection con;

    public UsuarioDAO() throws Exception {
        con = Conexion.conectar();
    }

    // ------------------ REGISTRAR ------------------
    public boolean registrar(Usuario u) {
        try {
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO usuario(nombre, usuCorreo, usuario, clave, usutelefono, direccion, fechaNacimiento, barrio, rol) VALUES(?,?,?,?,?,?,?,?,?)"
            );
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getUsuCorreo());
            ps.setString(3, u.getUsuario());
            ps.setString(4, u.getClave());
            ps.setString(5, u.getUsuTelefono());
            ps.setString(6, u.getDireccion());
            ps.setDate(7, new java.sql.Date(u.getFechaNacimiento().getTime())); // ✅ ahora es Date
            ps.setString(8, u.getBarrio());
            ps.setString(9, u.getRol());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ------------------ LISTAR ------------------
    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM usuario");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("pk_idusuario"));
                u.setNombre(rs.getString("nombre"));
                u.setUsuCorreo(rs.getString("usuCorreo"));
                u.setUsuario(rs.getString("usuario"));
                u.setClave(rs.getString("clave"));
                u.setUsuTelefono(rs.getString("usutelefono"));
                u.setDireccion(rs.getString("direccion"));
                u.setFechaNacimiento(rs.getDate("fechaNacimiento")); // ✅ recupera como Date
                u.setBarrio(rs.getString("barrio"));
                u.setRol(rs.getString("rol"));
                lista.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    // ------------------ ACTUALIZAR ------------------
    public boolean actualizar(Usuario u) {
        try {
            PreparedStatement ps = con.prepareStatement(
                "UPDATE usuario SET nombre=?, usuCorreo=?, usuario=?, clave=?, usutelefono=?, direccion=?, fechaNacimiento=?, barrio=?, rol=? WHERE pk_idusuario=?"
            );
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getUsuCorreo());
            ps.setString(3, u.getUsuario());
            ps.setString(4, u.getClave());
            ps.setString(5, u.getUsuTelefono());
            ps.setString(6, u.getDireccion());
            ps.setDate(7, new java.sql.Date(u.getFechaNacimiento().getTime())); // ✅
            ps.setString(8, u.getBarrio());
            ps.setString(9, u.getRol());
            ps.setInt(10, u.getId());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ------------------ ELIMINAR ------------------
    public boolean eliminar(int id) {
        try {
            PreparedStatement ps = con.prepareStatement("DELETE FROM usuario WHERE pk_idusuario=?");
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ------------------ LOGIN ------------------
    public Usuario login(String correo, String clave) {
        Usuario u = null;
        try {
            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM usuario WHERE usuCorreo=? AND clave=?"
            );
            ps.setString(1, correo);
            ps.setString(2, clave);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                u = new Usuario();
                u.setId(rs.getInt("pk_idusuario"));
                u.setNombre(rs.getString("nombre"));
                u.setUsuCorreo(rs.getString("usuCorreo"));
                u.setUsuario(rs.getString("usuario"));
                u.setClave(rs.getString("clave"));
                u.setUsuTelefono(rs.getString("usutelefono"));
                u.setDireccion(rs.getString("direccion"));
                u.setFechaNacimiento(rs.getDate("fechaNacimiento")); // ✅ recupera como Date
                u.setBarrio(rs.getString("barrio"));
                u.setRol(rs.getString("rol"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return u;
    }

public List<Usuario> listarUsuarios() {
    List<Usuario> lista = new ArrayList<>();
    try {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM usuario");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Usuario u = new Usuario();
            u.setId(rs.getInt("pk_idusuario"));
            u.setNombre(rs.getString("nombre"));
            u.setUsuCorreo(rs.getString("usuCorreo"));
            u.setUsuario(rs.getString("usuario"));
            u.setClave(rs.getString("clave"));
            u.setUsuTelefono(rs.getString("usutelefono"));
            u.setDireccion(rs.getString("direccion"));
            u.setFechaNacimiento(rs.getDate("fechaNacimiento"));
            u.setBarrio(rs.getString("barrio"));
            u.setRol(rs.getString("rol"));
            lista.add(u);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return lista;
}

    public int obtenerUltimoId() {
    try {
        PreparedStatement ps = con.prepareStatement("SELECT MAX(pk_idusuario) FROM usuario");
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return 0;
}

}
