package com.agglotek.insidesales.emailservice;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.InputStream;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;

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

    // New method to send email with attachments
    public void sendTemplateEmailWithAttachments(String to, String subject, String templateName, Map<String, String> variables, List<File> attachmentFiles)
            throws MessagingException, IOException {

        String htmlContent = templateLoader.loadRemoteTemplate(templateName);
        htmlContent = processTemplate(htmlContent, variables);

        sendHtmlEmailWithAttachments(to, subject, htmlContent, attachmentFiles);
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

    private void sendHtmlEmailWithAttachments(String to, String subject, String htmlContent, List<File> attachmentFiles) throws MessagingException {
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

        // Create the MimeMultipart container
        Multipart multipart = new MimeMultipart();

        // Part 1: HTML content
        BodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(htmlContent, "text/html; charset=UTF-8");
        multipart.addBodyPart(htmlPart);

        // Part 2: Attachments
        for (File file : attachmentFiles) {
            if (file.exists() && file.isFile()) {
                BodyPart attachmentPart = new MimeBodyPart();
                try (InputStream inputStream = new FileInputStream(file)) {
                    attachmentPart.setDataHandler(new jakarta.activation.DataHandler(new jakarta.activation.DataSource() {
                        @Override
                        public InputStream getInputStream() throws IOException {
                            return new FileInputStream(file);
                        }
                        @Override
                        public String getContentType() {
                            return "application/octet-stream";
                        }
                        @Override
                        public String getName() {
                            return file.getName();
                        }
                        @Override
                        public java.io.OutputStream getOutputStream() throws java.io.IOException {
                            return null;
                        }
                    }));
                    attachmentPart.setFileName(file.getName());
                    multipart.addBodyPart(attachmentPart);
                } catch (IOException e) {
                    System.err.println("Failed to attach file: " + file.getName());
                    e.printStackTrace();
                }
            } else {
                System.err.println("File not found or is a directory: " + file.getAbsolutePath());
            }
        }

        // Set the content of the message to the multipart object
        message.setContent(multipart);

        Transport.send(message);
    }

    private String processTemplate(String templateContent, Map<String, String> variables) {
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            templateContent = templateContent.replace("${" + entry.getKey() + "}", entry.getValue());
        }
        return templateContent;
    }
}
