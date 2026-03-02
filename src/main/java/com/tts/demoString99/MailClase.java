package com.tts.demoString99;

import lombok.extern.slf4j.Slf4j;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.List;
import java.util.Properties;


public class MailClase {

    public static   void sendMailAlert(

            String local
    ) {


        System.out.println(":::::::::::::::::::::::::SEND MAIL:::::::::::::::::::::::::::::::::::");

        // === DATOS DE CORREO (SMTP LOCAL) ===
        final String mailFrom = "no-reply@server.local";
        final String mailTo = "desarrollador2@tts.com.ec";

        String subject;
        String message;

        String fechaHora = java.time.ZonedDateTime
                .now(java.time.ZoneId.of("America/Guayaquil"))
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));


        String nota =
                "NOTA: La transacción se muestra en su formato original, tal como fue recibida desde el Agente.\n" +
                        "No se realizó la decodificación de campos utilizando el archivo mapeoempaquetado.txt.\n\n";



            subject = "TRANSACCIÓN VACÍA DETECTADA LOC: " + local;
            message =
                    "ALERTA: TRANSACCIÓN VACÍA DETECTADA\n" +
                            "============================================\n" +
                            "Local        : " + local + "\n" +
                            "Fecha / Hora : " + fechaHora + "\n\n" +
                            nota +
                            "Se recibió una transacción sin registros de detalle.\n\n" +
                            "Sistema de monitoreo automático.";



        try {
            // === SMTP LOCAL (igual que `mail`) ===
            Properties propertiesMail = new Properties();
            propertiesMail.put("mail.smtp.host", "localhost");
            propertiesMail.put("mail.smtp.port", "25");
            propertiesMail.put("mail.smtp.auth", "false");
            propertiesMail.put("mail.smtp.starttls.enable", "false");

            Session session = Session.getInstance(propertiesMail);

            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(mailFrom));
            mimeMessage.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(mailTo)
            );
            mimeMessage.setSubject(subject, "UTF-8");
            mimeMessage.setSentDate(new Date());

            // === TEXTO PLANO ===
            mimeMessage.setText(message, "UTF-8");

            Transport.send(mimeMessage);
            //log.info("Correo enviado correctamente a: " + mailTo);

        } catch (Exception e) {
           // log.error("Error enviando correo de alerta", e);
        }
    }
}
