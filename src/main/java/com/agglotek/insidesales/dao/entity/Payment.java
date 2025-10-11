package com.agglotek.insidesales.dao.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Integer paymentId;

    @Column(name = "invoice_id")
    private Integer invoiceId;

    @Column(name = "amount_received", precision = 15, scale = 2)
    private BigDecimal amountReceived;

    @Column(name = "payment_description", columnDefinition = "TEXT")
    private String paymentDescription;

    @Column(name = "project_id")
    private Integer projectId;

    @Column(name = "received_date")
    private LocalDate receivedDate;

    @Column(name = "payment_mode", length = 50)
    private String paymentMode;

    @Column(name = "currency", length = 10)
    private String currency;

    // Getters and Setters

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public BigDecimal getAmountReceived() {
        return amountReceived;
    }

    public void setAmountReceived(BigDecimal amountReceived) {
        this.amountReceived = amountReceived;
    }

    public String getPaymentDescription() {
        return paymentDescription;
    }

    public void setPaymentDescription(String paymentDescription) {
        this.paymentDescription = paymentDescription;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public LocalDate getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(LocalDate receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", invoiceId=" + invoiceId +
                ", amountReceived=" + amountReceived +
                ", paymentDescription='" + paymentDescription + '\'' +
                ", projectId=" + projectId +
                ", receivedDate=" + receivedDate +
                ", paymentMode='" + paymentMode + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }
}

