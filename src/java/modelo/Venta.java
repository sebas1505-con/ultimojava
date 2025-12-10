package modelo;

import java.io.Serializable;
import java.sql.Timestamp;

public class Venta implements Serializable {
    private int pk_idVenta;
    private Integer fk_id_detalle_venta; // puede ser null
    private int cantProducto;
    private String metodoEnvio;
    private double totalVenta;
    private String metodoDePago;
    private int idCliente;
    private String direccionEnvio;
    private String telefonoContacto;
    private String observaciones;
    private Timestamp fechaDeVenta;
    private String estado;

    // --- Getters y Setters ---
    public int getPk_idVenta() { return pk_idVenta; }
    public void setPk_idVenta(int pk_idVenta) { this.pk_idVenta = pk_idVenta; }

    public Integer getFk_id_detalle_venta() { return fk_id_detalle_venta; }
    public void setFk_id_detalle_venta(Integer fk_id_detalle_venta) { this.fk_id_detalle_venta = fk_id_detalle_venta; }

    public int getCantProducto() { return cantProducto; }
    public void setCantProducto(int cantProducto) { this.cantProducto = cantProducto; }

    public String getMetodoEnvio() { return metodoEnvio; }
    public void setMetodoEnvio(String metodoEnvio) { this.metodoEnvio = metodoEnvio; }

    public double getTotalVenta() { return totalVenta; }
    public void setTotalVenta(double totalVenta) { this.totalVenta = totalVenta; }

    public String getMetodoDePago() { return metodoDePago; }
    public void setMetodoDePago(String metodoDePago) { this.metodoDePago = metodoDePago; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public String getDireccionEnvio() { return direccionEnvio; }
    public void setDireccionEnvio(String direccionEnvio) { this.direccionEnvio = direccionEnvio; }

    public String getTelefonoContacto() { return telefonoContacto; }
    public void setTelefonoContacto(String telefonoContacto) { this.telefonoContacto = telefonoContacto; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public Timestamp getFechaDeVenta() { return fechaDeVenta; }
    public void setFechaDeVenta(Timestamp fechaDeVenta) { this.fechaDeVenta = fechaDeVenta; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
