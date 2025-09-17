package com.agglotek.insidesales.service.impl;

import com.agglotek.insidesales.dao.entity.Payment;
import com.agglotek.insidesales.repository.InvoiceRepository;
import com.agglotek.insidesales.repository.PaymentRepository;
import com.agglotek.insidesales.repository.ProjectRepository;
import com.agglotek.insidesales.service.api.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentServiceImpl implements IPaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public Payment createOrUpdatePayment(Payment request) {

        Payment payment;
        BigDecimal amountChange;

        if (request.getPaymentId() != null) {
            // Update existing payment
            payment = paymentRepository.findById(request.getPaymentId()).orElse(new Payment());

            // Calculate difference for balance adjustment
            BigDecimal previousAmount = payment.getAmountReceived() != null ? payment.getAmountReceived() : BigDecimal.ZERO;
            amountChange = request.getAmountReceived().subtract(previousAmount);

        } else {
            // New payment creation
            payment = new Payment();
            amountChange = request.getAmountReceived();
        }

        // Set payment fields
        payment.setInvoiceId(request.getInvoiceId());
        payment.setAmountReceived(request.getAmountReceived());
        payment.setPaymentDescription(request.getPaymentDescription());
        payment.setProjectId(request.getProjectId());
        payment.setReceivedDate(request.getReceivedDate());
        payment.setPaymentMode(request.getPaymentMode());

        Payment savedPayment = paymentRepository.save(payment);

        // Update the related Invoice balance_amt
        projectRepository.findById(request.getProjectId()).ifPresent(project -> {
            BigDecimal currentBalance = project.getBalanceAmt() != null ? project.getBalanceAmt() : BigDecimal.ZERO;
            project.setBalanceAmt(currentBalance.subtract(amountChange));
            projectRepository.save(project);
        });


        return savedPayment;
    }
}
