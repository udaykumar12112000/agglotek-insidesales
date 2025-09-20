package com.agglotek.insidesales.controller;


import com.agglotek.insidesales.ApiResponse;
import com.agglotek.insidesales.constants.ApiConstants;
import com.agglotek.insidesales.dao.entity.Invoice;
import com.agglotek.insidesales.dto.InvoiceResponseDto;
import com.agglotek.insidesales.service.api.IInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(
//        origins = "http://localhost:4200",
//        allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"},
//        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE, RequestMethod.OPTIONS},
//        allowCredentials = "false" // set true only if you use cookies
//)
@CrossOrigin(
        origins = {"http://localhost:4200"},
        allowedHeaders = "*"
)
@RequestMapping(ApiConstants.INVOICE)
public class InvoiceController {
    @Autowired
    private IInvoiceService invoiceService;

    @PostMapping(ApiConstants.CREATE_OR_UPDATE_INVOICE)
    public ResponseEntity<ApiResponse> createOrUpdateInvoice(@RequestHeader("User-Id") Integer userId, @RequestBody Invoice request) {

        if (request.getInvoiceNumber() == null || request.getInvoiceDate() == null
                || request.getProjectId() == null || request.getAmount() == null) {
            return ResponseEntity.status(400)
                    .body(new ApiResponse(false, "invoiceNumber, invoiceDate, projectId, and balanceAmt are required"));
        }

        Invoice invoice = invoiceService.createOrUpdateInvoice(userId, request);

        return ResponseEntity.ok(new ApiResponse(true, "Invoice saved successfully", invoice));
    }

    @GetMapping(ApiConstants.FETCH_INVOICE_BY_USERID)
    public ResponseEntity<List<InvoiceResponseDto>> getInvoices(
            @RequestHeader(value = "User-Id", required = false) Integer userId) {

        List<InvoiceResponseDto> invoices = invoiceService.getInvoices(userId);
        return ResponseEntity.ok(invoices);
    }


    @DeleteMapping(ApiConstants.DELETE_INVOICE_BY_ID)
    public ResponseEntity<String> deleteInvoice(@PathVariable Integer invoiceId) {
        invoiceService.deleteInvoice(invoiceId);
        return ResponseEntity.ok("Invoice deleted successfully with id: " + invoiceId);
    }
}
