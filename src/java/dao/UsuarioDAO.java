package dao;

import java.sql.*;
import modelo.Usuario;

public class UsuarioDAO {

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
    
    private String resetToken;
private Timestamp tokenExpiracion;

// Getters y setters
public String getResetToken() { return resetToken; }
public void setResetToken(String resetToken) { this.resetToken = resetToken; }

public Timestamp getTokenExpiracion() { return tokenExpiracion; }
public void setTokenExpiracion(Timestamp tokenExpiracion) { this.tokenExpiracion = tokenExpiracion; }

    
}



