package com.agglotek.insidesales.controller;

import com.agglotek.insidesales.ApiResponse;
import com.agglotek.insidesales.constants.ApiConstants;
import com.agglotek.insidesales.dao.entity.Payment;
import com.agglotek.insidesales.service.api.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping(ApiConstants.PAYMENTS)
public class PaymentController {

    @Autowired
    private IPaymentService paymentService;

    @PostMapping(ApiConstants.PAYMENT_CREATION)
    public ResponseEntity<ApiResponse> createOrUpdatePayment(@RequestBody Payment request) {

        if (request.getProjectId() == null || request.getInvoiceId() == null || request.getAmountReceived() == null) {
            return ResponseEntity.status(400)
                    .body(new ApiResponse(false, "projectId, invoiceId, and amountReceived are required"));
        }

        Payment payment = paymentService.createOrUpdatePayment(request);

        return ResponseEntity.ok(new ApiResponse(true, "Payment saved successfully", payment));
    }
}
