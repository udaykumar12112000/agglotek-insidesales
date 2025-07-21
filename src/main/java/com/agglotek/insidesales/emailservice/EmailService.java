package com.agglotek.insidesales.emailservice;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class EmailService {

    @Value(("${mailService.smtp.host}"))
    private final String smtpHost;

    @Value(("${mailService.smtp.port}"))
    private final int smtpPort;

    @Value(("${mailService.username}"))
    private final String username;

    @Value(("${mailService.password}"))
    private final String password;

    private final TemplateLoader templateLoader;

    public EmailService(String smtpHost, int smtpPort, String username, String password, TemplateLoader templateLoader) {
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
        this.username = username;
        this.password = password;
        this.templateLoader = templateLoader;
    }

    public void sendTemplateEmail(String to, String subject, String templateName, Map<String, String> variables)
            throws MessagingException, IOException {

        String htmlContent = templateLoader.loadRemoteTemplate(templateName);
        htmlContent = processTemplate(htmlContent, variables);

        sendHtmlEmail(to, subject, htmlContent);
    }

    private void sendHtmlEmail(String to, String subject, String htmlContent) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "mail.agglotekinc.com");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setContent(htmlContent, "text/html; charset=UTF-8");

        Transport.send(message);
    }

    private String processTemplate(String templateContent, Map<String, String> variables) {
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            templateContent = templateContent.replace("${" + entry.getKey() + "}", entry.getValue());
        }
        return templateContent;
    }
}
