
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelo.Pedido;
import modelo.Producto;
import modelo.Usuario;

/**
 *
 * @author USUARIO
 */
public class PedidoDAO {
    

public List<Pedido> obtenerUltimosPedidos() {
    List<Pedido> pedidos = new ArrayList<>();
    String sql = "SELECT p.id, p.fecha, p.total, u.id AS cliente_id, u.nombre " +
                 "FROM pedidos p JOIN usuarios u ON p.id_cliente = u.id " +
                 "ORDER BY p.fecha DESC LIMIT 10";

    try (Connection con = Conexion.getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Pedido pedido = new Pedido();
            pedido.setId(rs.getInt("id"));
            pedido.setFecha(rs.getDate("fecha"));
            pedido.setTotal(rs.getDouble("total"));

            Usuario cliente = new Usuario();
            cliente.setId(rs.getInt("cliente_id"));
            cliente.setNombre(rs.getString("nombre"));
            pedido.setCliente(cliente);

            // 🔹 cargar productos del pedido
            pedido.setProductos(obtenerProductosDePedido(pedido.getId()));

            pedidos.add(pedido);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return pedidos;
}

private List<Producto> obtenerProductosDePedido(int pedidoId) {
    List<Producto> productos = new ArrayList<>();
    String sql = "SELECT pr.id, pr.nombre, dp.cantidad " +
                 "FROM detalle_pedido dp JOIN productos pr ON dp.id_producto = pr.id " +
                 "WHERE dp.id_pedido = ?";

    try (Connection con = Conexion.Connection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, pedidoId);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Producto prod = new Producto();
                prod.setId(rs.getInt("id"));
                prod.setNombre(rs.getString("nombre"));
                prod.setCantidad(rs.getInt("cantidad"));
                productos.add(prod);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return productos;
}
}