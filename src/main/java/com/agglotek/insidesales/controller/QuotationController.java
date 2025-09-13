package com.agglotek.insidesales.controller;

import com.agglotek.insidesales.ApiResponse;
import com.agglotek.insidesales.constants.ApiConstants;
import com.agglotek.insidesales.constants.AppConstants;
import com.agglotek.insidesales.dao.entity.Quotation;
import com.agglotek.insidesales.dao.entity.User;
import com.agglotek.insidesales.dto.QuotationInfoDTO;
import com.agglotek.insidesales.repository.QuotationRepository;
import com.agglotek.insidesales.service.api.IFileService;
import com.agglotek.insidesales.service.api.IRoleService;
import com.agglotek.insidesales.service.api.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.agglotek.insidesales.service.api.IQuotationService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin(
        origins = {"http://localhost:4200"},
        allowedHeaders = "*"
)
@RequestMapping(ApiConstants.QUOTATION_APIS)
public class QuotationController {

    @Autowired
    private IQuotationService quotationService;

    @Autowired
    private QuotationRepository quotationRepository;

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IFileService fileService;

    @PostMapping(ApiConstants.ADD_QUOTATION)
    public ResponseEntity<ApiResponse> addQuotation(@RequestBody Quotation request, @RequestHeader("User-Id") Integer userId) {
        // Check if client_id exists in previous quotations
        boolean isNewClient = quotationService.existsByClientId(request.getClientId()) ? false : true;

        String newQuotationNumber = generateNextQuotationNumber();
        if(newQuotationNumber.isEmpty() || newQuotationNumber == null)
            return ResponseEntity.status(500).body(new ApiResponse(false, "Something went wrong"));

        Quotation quotation = new Quotation();
        quotation.setQuotationNumber(newQuotationNumber);
        quotation.setDateOfQuotation(request.getDateOfQuotation());
        quotation.setQuotationDueDate(request.getQuotationDueDate());
        quotation.setProjectName(request.getProjectName());
        quotation.setConnectionEngineering(request.getConnectionEngineering());
        quotation.setComments(request.getComments());
        quotation.setUserId(userId);
        quotation.setClientId(request.getClientId());
        quotation.setNewClient(isNewClient);
        quotation.setContactPersonName(request.getContactPersonName());
        quotation.setContactPersonNumber(request.getContactPersonNumber());
        quotation.setEstimatorId(request.getEstimatorId());
        quotation.setCreatedTime(LocalDateTime.now());
        quotation.setUpdatedTime(LocalDateTime.now());
        quotationService.addQuotation(quotation);

        return ResponseEntity.ok(new ApiResponse(true, "Quotation added successfully", quotation));
    }

    private String generateNextQuotationNumber() {
        // Get the current year in two-digit format (e.g., 2025 → "25")
        String yearSuffix = String.valueOf(LocalDate.now().getYear()).substring(2);

        // Define the prefix pattern for the year
        String prefix = "QO" + yearSuffix + "-";

        // Fetch the last quotation number that starts with this prefix
        String lastQuotationNumber = quotationRepository.findLastQuotationNumber(prefix + "%");

        int nextNumber = 1;

        if (lastQuotationNumber != null && lastQuotationNumber.startsWith(prefix)) {
            String[] parts = lastQuotationNumber.split("-");
            if (parts.length == 2) {
                try {
                    nextNumber = Integer.parseInt(parts[1]) + 1;
                } catch (NumberFormatException e) {
                    // default to 1 if parsing fails
                    nextNumber = 1;
                }
            }
        }

        // Format to 4-digit number with leading zeros
        String formattedNumber = String.format("%04d", nextNumber);

        return prefix + formattedNumber;
    }

    @GetMapping(ApiConstants.FILTER_QUOTATIONS)
    public ResponseEntity<List<QuotationInfoDTO>> getQuotationsByUserIdAndStatus(
            @RequestHeader("User-Id") Integer userId,
            @RequestParam(value = "status", required = false) List<String> quotationStatus) throws IOException {

        List<QuotationInfoDTO> quotations;

        if (quotationStatus == null) {
            quotations = quotationService.getAllQuotationsByUserId(userId);
        }
        else if ("all".equalsIgnoreCase(quotationStatus.getFirst())){
            quotations = quotationService.getAllQuotations();
        }
        else {
            quotations = quotationService.getQuotationsByUserIdAndStatus(userId, quotationStatus);
        }

        for(QuotationInfoDTO quotation : quotations) {
             quotation.setFilesList(fileService.listFiles(quotation.getQuotationNumber(), quotation.getProjectName(), quotation.getClientId()));
        }
        return ResponseEntity.ok(quotations);
    }

    @PostMapping(ApiConstants.UPDATE_QUOTATION)
    public ResponseEntity<ApiResponse> updateQuotation(@RequestBody QuotationInfoDTO dto) {
        try {
            quotationService.updateQuotation(dto);
            return ResponseEntity.ok(new ApiResponse(true,"Quotation updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to update quotation"));
        }
    }

    @GetMapping("/get_quotations_per_estimator")
    public ResponseEntity<ApiResponse> getQuotationsRelatedToEstimator(
            @RequestHeader("User-Id") Integer userId, @RequestParam(value = "roleName", required = false) String roleName) {

        if(roleName==null) {
            // Get logged-in user
            List<User> users = userService.getUserByUserId(userId);
            if (users.isEmpty()) {
                throw new RuntimeException("User not found");
            }
            User user = users.get(0);
            roleName = roleService.getRoleNameById(user.getRoleId());
        }

        List<QuotationInfoDTO> quotations = quotationService.getQuotationsForUser(userId, roleName);
        return ResponseEntity.ok(new ApiResponse(true, "Quotations fetched successfully", quotations));
    }

    @PutMapping("/assign_quotation")
    public ResponseEntity<ApiResponse> assignEstimator(
            @RequestParam Integer quotationId,
            @RequestParam Integer estimatorId,
            @RequestHeader("User-Id") Integer userId) {

        // Ensure only manager can reassign
        List<User> users = userService.getUserByUserId(userId);
        if (users.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        User user = users.get(0);
        String roleName = roleService.getRoleNameById(user.getRoleId());

        if (!"ESTIMATOR_MANAGER".equalsIgnoreCase(roleName)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ApiResponse(false, "You do not have permission to reassign quotations"));
        }

        quotationService.updateAssignedEstimator(quotationId, estimatorId);
        return ResponseEntity.ok(new ApiResponse(true, "Estimator reassigned successfully"));
    }

}
