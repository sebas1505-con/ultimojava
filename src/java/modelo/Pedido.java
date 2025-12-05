package modelo;

public class Pedido {
    private int id;
    private String cliente;
    private String producto;
    private String fecha;
    private double total;

    public Pedido(int id, String cliente, String producto, String fecha, double total) {
        this.id = id;
        this.cliente = cliente;
        this.producto = producto;
        this.fecha = fecha;
        this.total = total;
    }

    public int getId() { return id; }
    public String getCliente() { return cliente; }
    public String getProducto() { return producto; }
    public String getFecha() { return fecha; }
    public double getTotal() { return total; }
}
