package modelo;

import java.util.Date;

public class Usuario {
    private int id;
    private String nombre;
    private String usuCorreo;
    private String usuario;
    private String clave;
    private String usutelefono;
    private String direccion;
    private Date fechaNacimiento; // ✅ ahora es Date
    private String barrio;
    private String rol;

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getUsuCorreo() { return usuCorreo; }
    public void setUsuCorreo(String usuCorreo) { this.usuCorreo = usuCorreo; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }

    public String getUsuTelefono() { return usutelefono; }
    public void setUsuTelefono(String usutelefono) { this.usutelefono = usutelefono; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public Date getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(Date fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getBarrio() { return barrio; }
    public void setBarrio(String barrio) { this.barrio = barrio; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}
