package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.Administrador;

public class AdministradorDAO {

    private Connection con;

    // Constructor: inicializa la conexión
    public AdministradorDAO() {
        try {
            con = Conexion.conectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Registrar administrador
    public void registrarAdministrador(Administrador admin) throws Exception {
        String sql = "INSERT INTO administrador (admCorreo, usuario, contraseña, telefono, codigo) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, admin.getAdmCorreo());
        ps.setString(2, admin.getUsuario());
        ps.setString(3, admin.getContraseña());
        ps.setString(4, admin.getTelefono());
        ps.setString(5, admin.getCodigo());
        ps.executeUpdate();
    }

    // Listar administradores
    public List<Administrador> listarAdministradores() throws Exception {
        List<Administrador> lista = new ArrayList<>();
        String sql = "SELECT * FROM administrador";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            Administrador admin = new Administrador();
            admin.setPk_idAdministrador(rs.getInt("pk_idAdministrador"));
            admin.setAdmCorreo(rs.getString("admCorreo"));
            admin.setUsuario(rs.getString("usuario"));
            admin.setContraseña(rs.getString("contraseña"));
            admin.setTelefono(rs.getString("telefono"));
            admin.setCodigo(rs.getString("codigo"));
            lista.add(admin);
        }
        return lista;
    }

    // Contar administradores
    public int contarAdministradores() throws Exception {
        String sql = "SELECT COUNT(*) FROM administrador";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    // Login administrador
    public Administrador login(String correo, String clave) throws Exception {
        String sql = "SELECT * FROM administrador WHERE admCorreo = ? AND contraseña = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, correo);
        ps.setString(2, clave);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Administrador admin = new Administrador();
            admin.setPk_idAdministrador(rs.getInt("pk_idAdministrador"));
            admin.setAdmCorreo(rs.getString("admCorreo"));
            admin.setUsuario(rs.getString("usuario"));
            admin.setContraseña(rs.getString("contraseña"));
            admin.setTelefono(rs.getString("telefono"));
            admin.setCodigo(rs.getString("codigo"));
            return admin;
        }
        return null; // ❌ si no encuentra coincidencia
    }

    // Registrar (versión booleana)
    public boolean registrar(Administrador admin) {
        try {
            registrarAdministrador(admin);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
