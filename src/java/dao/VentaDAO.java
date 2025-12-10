package dao;

import modelo.Venta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class VentaDAO {

    /**
     * Guarda una venta en la base de datos
     */
    public boolean guardar(Venta v) {
        String sql = "INSERT INTO venta (fk_id_detalle_venta, cantProducto, metodoEnvio, totalVenta, metodo_de_pago, " +
                     "id_cliente, direccionEnvio, telefonoContacto, observaciones, estado) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // fk_id_detalle_venta puede ser null
            if (v.getFk_id_detalle_venta() != null) {
                ps.setInt(1, v.getFk_id_detalle_venta());
            } else {
                ps.setNull(1, java.sql.Types.INTEGER);
            }

            ps.setInt(2, v.getCantProducto());
            ps.setString(3, v.getMetodoEnvio());
            ps.setDouble(4, v.getTotalVenta());
            ps.setString(5, v.getMetodoDePago());
            ps.setInt(6, v.getIdCliente());
            ps.setString(7, v.getDireccionEnvio());
            ps.setString(8, v.getTelefonoContacto());
            ps.setString(9, v.getObservaciones());
            ps.setString(10, v.getEstado() != null ? v.getEstado() : "Pendiente");

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Error al guardar venta: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Lista todas las ventas ordenadas por fecha
     */
    public List<Venta> listar() {
        List<Venta> lista = new ArrayList<>();
        String sql = "SELECT * FROM venta ORDER BY Fecha_de_venta DESC";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Venta v = new Venta();
                v.setPk_idVenta(rs.getInt("pk_idVenta"));

                // cuidado con valores nulos en fk_id_detalle_venta
                int fkDetalle = rs.getInt("fk_id_detalle_venta");
                if (!rs.wasNull()) {
                    v.setFk_id_detalle_venta(fkDetalle);
                }

                v.setCantProducto(rs.getInt("cantProducto"));
                v.setMetodoEnvio(rs.getString("metodoEnvio"));
                v.setTotalVenta(rs.getDouble("totalVenta"));
                v.setMetodoDePago(rs.getString("metodo_de_pago"));
                v.setIdCliente(rs.getInt("id_cliente"));
                v.setDireccionEnvio(rs.getString("direccionEnvio"));
                v.setTelefonoContacto(rs.getString("telefonoContacto"));
                v.setObservaciones(rs.getString("observaciones"));
                v.setFechaDeVenta(rs.getTimestamp("Fecha_de_venta"));
                v.setEstado(rs.getString("estado"));
                lista.add(v);
            }
        } catch (Exception e) {
            System.err.println("Error al listar ventas: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Elimina una venta por su ID
     */
    public boolean eliminar(int idVenta) {
        String sql = "DELETE FROM venta WHERE pk_idVenta=?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idVenta);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Error al eliminar venta: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
