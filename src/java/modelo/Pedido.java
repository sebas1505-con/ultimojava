package modelo;

import java.util.Date;
import java.util.List;

public class Pedido {
    private int id;
    private Usuario cliente;
    private Date fecha;
    private double total;
    private List<Producto> productos; // 🔹 lista de productos

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Usuario getCliente() { return cliente; }
    public void setCliente(Usuario cliente) { this.cliente = cliente; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public List<Producto> getProductos() { return productos; }
    public void setProductos(List<Producto> productos) { this.productos = productos; }
}


