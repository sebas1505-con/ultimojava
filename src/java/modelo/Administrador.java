package modelo;

public class Administrador {

    private int pk_idAdministrador;
    private String admCorreo;
    private String usuario;
    private String contraseña;
    private String telefono;
    private String codigo;
    private String rol;

    
   public String getRol() { return rol; }
   public void setRol(String rol) { this.rol = rol; }


    public int getPk_idAdministrador() {
        return pk_idAdministrador;
    }

    public void setPk_idAdministrador(int pk_idAdministrador) {
        this.pk_idAdministrador = pk_idAdministrador;
    }

    public String getAdmCorreo() {
        return admCorreo;
    }

    public void setAdmCorreo(String admCorreo) {
        this.admCorreo = admCorreo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
