package beans;

import dao.VentaDAO;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedProperty;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import modelo.Venta;

@ManagedBean(name="ventaBean")
@SessionScoped
public class VentaBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private int cantProducto;
    private double totalVenta;
    private String metodoEnvio;
    private String metodoPago;
    private String direccionEnvio;
private String telefonoContacto;
private String observaciones;

    

    @ManagedProperty(value="#{carritoBean}")
    private CarritoBean carritoBean; // NO debe ser static

    public CarritoBean getCarritoBean() { return carritoBean; }
    public void setCarritoBean(CarritoBean carritoBean) { this.carritoBean = carritoBean; }

    public void inicializar() {
        if (carritoBean != null) {
            this.cantProducto = carritoBean.getCantidadProductos();
            this.totalVenta = carritoBean.getTotal();
        }
    }

    // Getters y setters
    public int getCantProducto() {
        return cantProducto;
    }

    public void setCantProducto(int cantProducto) {
        this.cantProducto = cantProducto;
    }

    public String getMetodoEnvio() {
        return metodoEnvio;
    }

    public void setMetodoEnvio(String metodoEnvio) {
        this.metodoEnvio = metodoEnvio;
    }

    public double getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(double totalVenta) {
        this.totalVenta = totalVenta;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String guardarVenta() {
    Venta v = new Venta();
    v.setCantProducto(cantProducto);
    v.setMetodoEnvio(metodoEnvio);
    v.setTotalVenta(totalVenta);
    v.setMetodoDePago(metodoPago);
    v.setIdCliente(carritoBean.getCliente().getId()); // ejemplo
    v.setDireccionEnvio(direccionEnvio);
    v.setTelefonoContacto(telefonoContacto);
    v.setObservaciones(observaciones);
    v.setEstado("Pendiente");

    VentaDAO dao = new VentaDAO();
    boolean ok = dao.guardar(v);
    v.setIdCliente(1);


    if (ok) {
        // redirige a la vista de confirmación/factura
        return "confirmacion?faces-redirect=true";
    } else {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo guardar la venta"));
        return null;
    }
}
public String getDireccionEnvio() { return direccionEnvio; }
public void setDireccionEnvio(String direccionEnvio) { this.direccionEnvio = direccionEnvio; }

public String getTelefonoContacto() { return telefonoContacto; }
public void setTelefonoContacto(String telefonoContacto) { this.telefonoContacto = telefonoContacto; }

public String getObservaciones() { return observaciones; }
public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

}
