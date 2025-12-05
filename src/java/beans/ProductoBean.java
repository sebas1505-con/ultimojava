
package beans;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import modelo.Producto;

@ManagedBean(name="productoBean")
@SessionScoped
public class ProductoBean implements Serializable {
    private List<Producto> listaProd;
    private Producto producto;   // 👈 nuevo atributo
    private String imagen;       // 👈 para el inputFile

    public ProductoBean() {
        listaProd = new ArrayList<>();
        producto = new Producto(); // inicializar para el formulario
    }

    public List<Producto> getListaProd() { return listaProd; }
    public void setListaProd(List<Producto> listaProd) { this.listaProd = listaProd; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }

    // Método para guardar
    public void guardar() {
        listaProd.add(producto);
        producto = new Producto(); // reiniciar para el siguiente
    }
}
