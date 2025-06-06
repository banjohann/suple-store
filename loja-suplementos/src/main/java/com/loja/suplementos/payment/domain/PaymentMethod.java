package com.loja.suplementos.payment.domain;

public enum PaymentMethod {
    BOLETO("Boleto Bancário"),
    CREDIT_CARD("Cartão de Crédito"),
    DEBIT_CARD("Cartão de Débito"),
    PIX("Pix");

    private final String description;

    PaymentMethod(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}