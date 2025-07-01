package com.banjohann.lojasuplementos.model;

public class Shipping {

    Long id;

    String trackingNumber;

    String statusDescription;

    ShippingStatus status;

    DeliveryAddress deliveryAddress;

    public Shipping(Long id, String trackingNumber, String statusDescription, ShippingStatus status, DeliveryAddress deliveryAddress) {
        this.id = id;
        this.trackingNumber = trackingNumber;
        this.statusDescription = statusDescription;
        this.status = status;
        this.deliveryAddress = deliveryAddress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(DeliveryAddress deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public ShippingStatus getStatus() {
        return status;
    }

    public void setStatus(ShippingStatus status) {
        this.status = status;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }
}
