package beans;

import dao.UsuarioDAO;
import modelo.Usuario;
import util.Correo;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.util.UUID;
import java.sql.Timestamp;

@ManagedBean(name = "contraBean")
@RequestScoped
public class ContraBean {

    private String correo;
    private String mensaje;

    public void enviarCorreoConfirmacion() {
        try {
            UsuarioDAO dao = new UsuarioDAO();
            Usuario u = dao.buscarPorCorreo(correo);

            if (u != null) {
                String token = UUID.randomUUID().toString();
                Timestamp expiracion = new Timestamp(System.currentTimeMillis() + 30 * 60 * 1000);

                dao.guardarToken(u.getId(), token, expiracion);

                String link = "http://localhost:8080/Deportes360/restablecer.xhtml?token=" + token;

                String cuerpo = "<h3>Hola!</h3>"
                        + "<p>Haz solicitado cambiar tu contraseña. Haz click en el link:</p>"
                        + "<a href='" + link + "'>Cambiar mi contraseña</a>";

                Correo.enviarCorreoConfirmacion(correo,
                        "Confirmación de cambio de contraseña", cuerpo);

                mensaje = "Se ha enviado un correo de confirmación. Revisa tu bandeja.";
            } else {
                mensaje = "El correo ingresado no está registrado.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            mensaje = "Ocurrió un error al enviar el correo. Verifica tu configuración.";
        }
    }

    // Getters y setters
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}


