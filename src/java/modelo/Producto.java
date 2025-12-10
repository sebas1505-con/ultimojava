package modelo;

import java.util.Date;

public class Producto {
    private String cod;
    private String nombre;
    private String descr;
    private int exist;
    private double precio;
    private Date fven;

    // Getters y setters
    public String getCod() { return cod; }
    public void setCod(String cod) { this.cod = cod; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescr() { return descr; }
    public void setDescr(String descr) { this.descr = descr; }

    public int getExist() { return exist; }
    public void setExist(int exist) { this.exist = exist; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public Date getFven() { return fven; }
    public void setFven(Date fven) { this.fven = fven; }

    public void setCantidad(int aInt) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setId(int aInt) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
