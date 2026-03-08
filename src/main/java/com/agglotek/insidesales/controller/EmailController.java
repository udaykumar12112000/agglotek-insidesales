package com.agglotek.insidesales.controller;

import com.agglotek.insidesales.ApiResponse;
import com.agglotek.insidesales.constants.ApiConstants;
import com.agglotek.insidesales.constants.AppConstants;
import com.agglotek.insidesales.dao.entity.User;
import com.agglotek.insidesales.dto.QuotationInfoDTO;
import com.agglotek.insidesales.emailservice.EmailService;
import com.agglotek.insidesales.emailservice.MailSender;
import com.agglotek.insidesales.service.api.IRoleService;
import com.agglotek.insidesales.service.api.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.annotation.PostConstruct;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@RestController
// @CrossOrigin(
//         origins = "http://localhost:4200",
//         allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"},
//         methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE, RequestMethod.OPTIONS},
//         allowCredentials = "false" // set true only if you use cookies
// )

@CrossOrigin(
       origins = {"http://localhost:4200"},
       allowedHeaders = "*"
)
@RequestMapping(ApiConstants.EMAIL_SENDING)
public class EmailController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private MailSender mailSender;

//    @Value("${mailService.email.Admins}")
    public List<String> admins;

    public static final String TEMPLATE_BASE_URL = "https://agglotekinc.com/test";

    @PostMapping(ApiConstants.SEND_EMAIL)
    public ResponseEntity<ApiResponse> sendCommunicationEmail(
            @RequestParam Integer fromId,
            @RequestParam Integer sendtoId,
            @RequestParam String status,
            @RequestParam(required = false) List<MultipartFile> files,
            @RequestParam String number) {
        try {
            // 1. Fetch user details
            User fromUser = userService.getUserByUserId(fromId).stream().findFirst().orElse(null);
            User toUser = userService.getUserByUserId(sendtoId).stream().findFirst().orElse(null);
            String emailUsers = userService.getAcctMail();

            if (fromUser == null || toUser == null) {
                return ResponseEntity.badRequest().body(new ApiResponse(false, "Invalid fromId or sendtoId"));
            }

            // 2. Convert MultipartFiles → File
            List<File> attachmentFiles = new ArrayList<>();
            if (files != null) {
                for (MultipartFile mf : files) {
                    File convFile = File.createTempFile("attach-", mf.getOriginalFilename());
                    mf.transferTo(convFile);
                    attachmentFiles.add(convFile);
                }
            }

            // 3. Common email details
            String templateBaseUrl = TEMPLATE_BASE_URL;
            String subject = "Quotation/Project Update:: " + status;

            // === First email (toUser) ===
            Map<String, String> variables = new HashMap<>();
            variables.put("companyName", "Agglotek");
            variables.put("quotationNumber", number);

            String templateName;
            switch (status.toLowerCase()) {
                case "null":
                    templateName = "quotation_request";
                    variables.put("estimatorName", toUser.getName());
                    variables.put("salesPersonName", fromUser.getName());
                    break;
                case "in_proposal":
                    templateName = "quotation_estimated";
                    variables.put("estimatorName", fromUser.getName());
                    variables.put("salesPersonName", toUser.getName());
                    break;
                case "revised":
                    templateName = "quotation_reestimated";
                    variables.put("estimatorName", fromUser.getName());
                    variables.put("salesPersonName", toUser.getName());
                    break;
                case "need_changes":
                    templateName = "quotation_changes_requested";
                    variables.put("estimatorName", toUser.getName());
                    variables.put("salesPersonName", fromUser.getName());
                    break;
                case "assign_estimator":
                    templateName = "assign_estimator";
                    variables.put("estimatorName", toUser.getName());
                    variables.put("salesPersonName", fromUser.getName());
                    break;
                case "assign_project_manager":
                    templateName = "assign_project_manager";
                    variables.put("estimatorName", toUser.getName());
                    variables.put("salesPersonName", fromUser.getName());
                    break;
                case "accountant_notify":
                    templateName = "accountant_notification";
                    variables.put("estimatorName", emailUsers);
                    variables.put("salesPersonName", fromUser.getName());
                    break;

                default:
                    templateName = "quotation_request";
                    break;
            }

            mailSender.sendEmailWithAttachments(
                    templateBaseUrl,
                    Collections.singletonList(toUser.getEmail()),
                    subject,
                    templateName,
                    attachmentFiles,
                    variables
            );

            // === Second email (admins) ===
            Map<String, String> adminVariables = new HashMap<>();
            adminVariables.put("companyName", "Agglotek");
            adminVariables.put("recipientName", "Admin");
            adminVariables.put("updatedBy", fromUser.getName());
            adminVariables.put("newStatus", status);
            adminVariables.put("quotationNumber", number);

            mailSender.sendEmailWithAttachments(
                    templateBaseUrl,
                    admins,
                    subject,
                    "project_status",
                    attachmentFiles,
                    adminVariables
            );

            return ResponseEntity.ok(new ApiResponse(true,
                    "Email sent successfully from " + fromUser.getEmail() + " to " + toUser.getEmail()));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to send Email: " + e.getMessage()));
        }
    }

    @PostConstruct
    public void init() {
        List<User> adminList = userService.getUsersByRoleId(roleService.getRoleIdByRoleName(AppConstants.ADMIN));
        admins = adminList.stream()
                .map(User::getEmail)
                .collect(Collectors.toList());
        System.out.println("Loaded admin emails in controller: " + admins);
    }
}
