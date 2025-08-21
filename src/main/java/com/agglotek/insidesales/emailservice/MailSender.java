package com.agglotek.insidesales.emailservice;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MailSender {

    private final String smtpHost;
    private final int smtpPort;
    private final String username;
    private final String password;

    public MailSender(
            @Value("${mailService.smtp.host}")
            String smtpHost,
            @Value("${mailService.smtp.port}")
            int smtpPort,
            @Value("${mailService.username}")
            String username,
            @Value("${mailService.password}")
            String password) {
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
        this.username = username;
        this.password = password;
    }

    public void sendEmail(String templateBaseUrl, List sendToEmailList, String subject, String templateName) {
        //String templateBaseUrl = "https://agglotekinc.com/test"; // must be public
        TemplateLoader loader = new TemplateLoader(templateBaseUrl);

        EmailService emailService = new EmailService(smtpHost, smtpPort, username, password, loader);

        Map<String, String> variables = new HashMap<>();
        variables.put("companyName", "Agglotek");
        variables.put("customerName", "Udhay");

        try {
//            emailService.sendTemplateEmail(
//                    "udaykumar12112000@gmail.com,jaanumudhiraj1977@gmail.com,udaydumma232@gmail.com",
//                    "Welcome to InsideSales!",
//                    "file", // welcome.html
//                    variables
//            );
            emailService.sendTemplateEmail(
                    sendToEmailList.toString(),
                    subject,
                    templateName, // welcome.html
                    variables
            );
            System.out.println("Email sent.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendEmailWithAttachments(String templateBaseUrl, List<String> sendToEmailList, String subject, String templateName, List<File> attachmentFiles, Map<String, String> variables) {
        TemplateLoader loader = new TemplateLoader(templateBaseUrl);
        EmailService emailService = new EmailService(smtpHost, smtpPort, username, password, loader);

        try {
            String body = loader.getTemplate(templateName, variables);
            // Check if attachment files are provided
            if (attachmentFiles != null && !attachmentFiles.isEmpty()) {
                System.out.println("Sending email with attachments...");
                emailService.sendTemplateEmailWithAttachments(
                        String.join(",", sendToEmailList),
                        subject,
                        body,
                        variables,
                        attachmentFiles
                );
            } else {
                System.out.println("Sending text-only email...");
                emailService.sendTemplateEmail(
                        String.join(",", sendToEmailList),
                        subject,
                        body,
                        variables
                );
            }
            System.out.println("Email sent successfully.");
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
            System.err.println("Failed to send email.");
        }
    }
}
