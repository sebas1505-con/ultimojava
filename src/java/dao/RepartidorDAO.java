package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Repartidor;

public class RepartidorDAO {

    private Connection con;

    public RepartidorDAO() {
        try {
            con = Conexion.conectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Registrar repartidor
    public boolean registrar(Repartidor r) {
        String sql = "INSERT INTO repartidor (repTelefono, fk_id_usuario, tipodevehi, numplaca, NombreRepar, Correo, Usuario, contrasena, rol) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, r.getRepTelefono());
            ps.setObject(2, r.getIdUsuario(), java.sql.Types.INTEGER);
            ps.setString(3, r.getTipoDeVehi());
            ps.setString(4, r.getNumPlaca());
            ps.setString(5, r.getNombreRepar());
            ps.setString(6, r.getCorreo());
            ps.setString(7, r.getUsuario());
            ps.setString(8, r.getContrasena());
            ps.setString(9, r.getRol());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Listar repartidores
    public List<Repartidor> listar() {
        List<Repartidor> lista = new ArrayList<>();
        String sql = "SELECT * FROM repartidor";
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Repartidor r = new Repartidor();
                r.setIdRepartidor(rs.getInt("pk_idRepartidor"));
                r.setRepTelefono(rs.getString("repTelefono"));
                r.setIdUsuario((Integer) rs.getObject("fk_id_usuario"));
                r.setTipoDeVehi(rs.getString("tipodevehi"));
                r.setNumPlaca(rs.getString("numplaca"));
                r.setNombreRepar(rs.getString("NombreRepar"));
                r.setCorreo(rs.getString("Correo"));
                r.setUsuario(rs.getString("Usuario"));
                r.setContrasena(rs.getString("contrasena"));
                r.setRol(rs.getString("rol"));
                lista.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Actualizar repartidor
    public boolean actualizar(Repartidor r) {
        String sql = "UPDATE repartidor SET repTelefono=?, fk_id_usuario=?, tipodevehi=?, numplaca=?, NombreRepar=?, Correo=?, Usuario=?, contrasena=?, rol=? "
                   + "WHERE pk_idRepartidor=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, r.getRepTelefono());
            ps.setObject(2, r.getIdUsuario(), java.sql.Types.INTEGER);
            ps.setString(3, r.getTipoDeVehi());
            ps.setString(4, r.getNumPlaca());
            ps.setString(5, r.getNombreRepar());
            ps.setString(6, r.getCorreo());
            ps.setString(7, r.getUsuario());
            ps.setString(8, r.getContrasena());
            ps.setString(9, r.getRol());
            ps.setInt(10, r.getIdRepartidor());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar repartidor
    public boolean eliminar(int idRepartidor) {
        String sql = "DELETE FROM repartidor WHERE pk_idRepartidor=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idRepartidor);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Login repartidor
    public Repartidor login(String usuario, String clave) {
        String sql = "SELECT * FROM repartidor WHERE Usuario=? AND contrasena=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario);
            ps.setString(2, clave);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Repartidor r = new Repartidor();
                r.setIdRepartidor(rs.getInt("pk_idRepartidor"));
                r.setRepTelefono(rs.getString("repTelefono"));
                r.setIdUsuario((Integer) rs.getObject("fk_id_usuario"));
                r.setTipoDeVehi(rs.getString("tipodevehi"));
                r.setNumPlaca(rs.getString("numplaca"));
                r.setNombreRepar(rs.getString("NombreRepar"));
                r.setCorreo(rs.getString("Correo"));
                r.setUsuario(rs.getString("Usuario"));
                r.setContrasena(rs.getString("contrasena"));
                r.setRol(rs.getString("rol"));
                return r;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // si no encuentra coincidencia
    }
}
