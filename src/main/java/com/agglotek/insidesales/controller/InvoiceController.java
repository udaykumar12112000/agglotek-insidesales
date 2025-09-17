package com.agglotek.insidesales.controller;


import com.agglotek.insidesales.ApiResponse;
import com.agglotek.insidesales.constants.ApiConstants;
import com.agglotek.insidesales.dao.entity.Invoice;
import com.agglotek.insidesales.service.api.IInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ApiResponse> createOrUpdateInvoice(@RequestBody Invoice request) {

        if (request.getInvoiceNumber() == null || request.getInvoiceDate() == null
                || request.getProjectId() == null || request.getAmount() == null) {
            return ResponseEntity.status(400)
                    .body(new ApiResponse(false, "invoiceNumber, invoiceDate, projectId, and balanceAmt are required"));
        }

        Invoice invoice = invoiceService.createOrUpdateInvoice(request);

        return ResponseEntity.ok(new ApiResponse(true, "Invoice saved successfully", invoice));
    }
}
