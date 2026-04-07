package org.example.proyecto_tfg.service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class EmailService {

    private static final String EMAIL_REMITENTE = "ipadsal809@g.educaand.es";
    //a24pasaiv@iesgrancapitan.org
    private static final String CONTRASENA = "hsyu laqf sudf cxyv"; //
    //hsyu laqf sudf cxyv

    public void enviarEmailBienvenida(String nombreMecanico, String correoDestino) {

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_REMITENTE, CONTRASENA);
            }
        });

        try {
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(EMAIL_REMITENTE));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correoDestino));
            message.setSubject("¡Bienvenido a BMS!");

            String htmlContent = crearHTMLBienvenida(nombreMecanico);
            message.setContent(htmlContent, "text/html; charset=UTF-8");

            Transport.send(message);

            System.out.println("✅ Email enviado a: " + correoDestino);

        } catch (MessagingException e) {
            System.err.println("❌ Error al enviar el email: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String crearHTMLBienvenida(String nombreMecanico) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String fecha = LocalDateTime.now().format(formatter);

        return """
                <!DOCTYPE html>
                <html lang="es">
                <head>
                    <meta charset="UTF-8">
                    <style>
                        body { font-family: Arial, sans-serif; background-color: #f4f4f4; }
                        .container { max-width: 600px; margin: auto; background-color: white; padding: 20px; border-radius: 8px; }
                        .header { text-align: center; color: #4CAF50; }
                        .content { color: #555; line-height: 1.6; }
                        .footer { text-align: center; color: #999; font-size: 12px; margin-top: 20px; }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <div class="header">
                            <h1>🚲 Sistema de Gestión de Bicicletas</h1>
                        </div>
                        <div class="content">
                            <p>¡Hola <strong>%s</strong>!</p>
                            <p>Te damos la bienvenida al sistema.</p>
                            
                            <ul>
                                <li>✅ Gestionar bicicletas</li>
                                <li>👥 Administrar clientes</li>
                                <li>🔧 Registrar mantenimientos</li>
                            </ul>

                            <p><strong>Fecha de acceso:</strong> %s</p>
                        </div>
                        <div class="footer">
                            <p>Este es un email automático.</p>
                        </div>
                    </div>
                </body>
                </html>
                """.formatted(nombreMecanico, fecha);
    }
}