package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Usuario;
import org.primefaces.component.keyboard.KeyboardBase.PropertyKeys;

public class UsuarioDAO {

    public static Usuario validarUsuario(String correo, String clave) {
    String sql = "SELECT * FROM usuario WHERE usuCorreo=? AND clave=?";
    try (Connection con = Conexion.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, correo);
        ps.setString(2, clave);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
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
                u.setResetToken(rs.getString("reset_token"));
                u.setTokenExpiracion(rs.getTimestamp("token_expiracion"));
                return u;
            }
        }
    } catch (Exception e) {
        System.err.println("Error al validar usuario: " + e.getMessage());
        e.printStackTrace();
    }
    return null;
}

    private Connection con;

    public UsuarioDAO() {
        try {
            con = Conexion.conectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Buscar usuario por correo
    public Usuario buscarPorCorreo(String correo) {
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM usuario WHERE usuCorreo=?");
            ps.setString(1, correo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
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
                u.setResetToken(rs.getString("reset_token"));
                u.setTokenExpiracion(rs.getTimestamp("token_expiracion"));
                return u;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Guardar token y expiración
    public boolean guardarToken(int id, String token, Timestamp expiracion) {
        try {
            PreparedStatement ps = con.prepareStatement(
                "UPDATE usuario SET reset_token=?, token_expiracion=? WHERE pk_idusuario=?"
            );
            ps.setString(1, token);
            ps.setTimestamp(2, expiracion);
            ps.setInt(3, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Actualizar contraseña por token
    public boolean actualizarContrasenaPorToken(String token, String nuevaClave) {
        try {
            PreparedStatement ps = con.prepareStatement(
                "UPDATE usuario SET clave=?, reset_token=NULL, token_expiracion=NULL WHERE reset_token=? AND token_expiracion > NOW()"
            );
            ps.setString(1, nuevaClave);
            ps.setString(2, token);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Registrar usuario
    public boolean registrar(Usuario u) {
        String sql = "INSERT INTO usuario (nombre, usuCorreo, usuario, clave, usutelefono, direccion, fechaNacimiento, barrio, rol) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getUsuCorreo());
            ps.setString(3, u.getUsuario());
            ps.setString(4, u.getClave());
            ps.setString(5, u.getUsuTelefono());
            ps.setString(6, u.getDireccion());
            ps.setDate(7, new java.sql.Date(u.getFechaNacimiento().getTime()));
            ps.setString(8, u.getBarrio());
            ps.setString(9, u.getRol());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Listar usuarios
    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario";
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
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
                u.setResetToken(rs.getString("reset_token"));
                u.setTokenExpiracion(rs.getTimestamp("token_expiracion"));
                lista.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Actualizar usuario
    public boolean actualizar(Usuario u) {
        String sql = "UPDATE usuario SET nombre=?, usuCorreo=?, usuario=?, clave=?, usutelefono=?, direccion=?, fechaNacimiento=?, barrio=?, rol=? "
                   + "WHERE pk_idusuario=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getUsuCorreo());
            ps.setString(3, u.getUsuario());
            ps.setString(4, u.getClave());
            ps.setString(5, u.getUsuTelefono());
            ps.setString(6, u.getDireccion());
            ps.setDate(7, new java.sql.Date(u.getFechaNacimiento().getTime()));
            ps.setString(8, u.getBarrio());
            ps.setString(9, u.getRol());
            ps.setInt(10, u.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar usuario
    public boolean eliminar(int id) {
        String sql = "DELETE FROM usuario WHERE pk_idusuario=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
