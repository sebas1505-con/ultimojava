package util;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class Correo {

    private static final String USERNAME = "tucorreo@gmail.com"; // tu correo Gmail
    private static final String PASSWORD = "tu_app_password";   // contraseña de app

    public static void enviarCorreoConfirmacion(String destinatario, String asunto, String cuerpo) throws Exception {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(USERNAME));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
        message.setSubject(asunto);
        message.setContent(cuerpo, "text/html; charset=utf-8");

        Transport.send(message);
    }
}
