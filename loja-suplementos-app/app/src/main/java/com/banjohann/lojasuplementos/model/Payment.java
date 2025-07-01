package com.banjohann.lojasuplementos.model;

import java.math.BigDecimal;
import java.util.Date;

public class Payment {

    Long id;

    PaymentMethod paymentMethod;

    PaymentStatus status;

    BigDecimal amount;

    Date transactionDate;

    public Payment(Long id, PaymentMethod paymentMethod, PaymentStatus paymentStatus, BigDecimal amount, Date transactionDate) {
        this.id = id;
        this.paymentMethod = paymentMethod;
        this.status = paymentStatus;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
}
