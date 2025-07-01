package com.banjohann.lojasuplementos.model;

public enum ShippingStatus {
    NOT_SHIPPED("Não enviado"),
    SHIPPED("Enviado"),
    LAST_STEP("Última etapa"),
    DELIVERED("Entregue"),
    NOT_DELIVERED("Não entregue");

    private final String descritption;

    ShippingStatus(String descritption) {
        this.descritption = descritption;
    }
}
