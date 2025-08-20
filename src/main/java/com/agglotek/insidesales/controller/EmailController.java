package com.agglotek.insidesales.controller;

import com.agglotek.insidesales.ApiResponse;
import com.agglotek.insidesales.constants.ApiConstants;
import com.agglotek.insidesales.dao.entity.User;
import com.agglotek.insidesales.dto.QuotationInfoDTO;
import com.agglotek.insidesales.emailservice.EmailService;
import com.agglotek.insidesales.emailservice.MailSender;
import com.agglotek.insidesales.service.api.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

@RestController
@CrossOrigin(
        origins = {"http://localhost:4200"},
        allowedHeaders = "*"
)
@RequestMapping(ApiConstants.EMAIL_SENDING)
public class EmailController {

    @Autowired
    private IUserService userService;

    @Autowired
    private MailSender mailSender;

    public static final String TEMPLATE_BASE_URL = "https://agglotekinc.com/test";

    @PostMapping(ApiConstants.SEND_EMAIL)
    public ResponseEntity<ApiResponse> sendCommunicationEmail(
                                                        @RequestParam Integer fromId,
                                                        @RequestParam Integer sendtoId,
                                                        @RequestParam String status,
                                                        @RequestParam(required = false) List<MultipartFile> files) {
        try {
            // 1. Fetch user details using service
            List<User> fromUsers = userService.getUserByUserId(fromId);
            List<User> toUsers = userService.getUserByUserId(sendtoId);

            if (fromUsers == null || fromUsers.isEmpty() || toUsers == null || toUsers.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse(false, "Invalid fromId or sendtoId"));
            }

            User fromUser = fromUsers.get(0);
            User toUser = toUsers.get(0);

            // 2. Convert MultipartFiles → File (temp storage)
            List<File> attachmentFiles = new ArrayList<>();
            if (files != null && !files.isEmpty()) {
                for (MultipartFile mf : files) {
                    File convFile = File.createTempFile("attach-", mf.getOriginalFilename());
                    mf.transferTo(convFile);
                    attachmentFiles.add(convFile);
                }
            }

            // 3. Prepare email details
            String templateBaseUrl =  TEMPLATE_BASE_URL;  // adjust to your path
            String subject = "Communication Update: " + status;
            String templateName = "communication_template.html";

            List<String> sendToEmails = Collections.singletonList(toUser.getEmail());

            // Add dynamic variables (example)
            Map<String, String> variables = new HashMap<>();
            variables.put("companyName", "Agglotek");
            variables.put("customerName", toUser.getName());
            variables.put("statusMessage", status);

            // 4. Call email service
            mailSender.sendEmailWithAttachments(templateBaseUrl, sendToEmails, subject, templateName, attachmentFiles);

            return ResponseEntity.ok(new ApiResponse(true, "Email sent successfully from "
                    + fromUser.getEmail() + " to " + toUser.getEmail()));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to send Email: " + e.getMessage()));
        }
    }

}
