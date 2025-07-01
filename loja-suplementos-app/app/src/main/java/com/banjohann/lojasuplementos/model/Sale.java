package com.banjohann.lojasuplementos.model;

import java.util.Date;

public class Sale {

    Long id;

    Date dateCreated;

    Payment payment;

    Customer customer;

    Shipping shipping;

    public Sale(Long id, Date dateCreated, Payment payment, Customer customer, Shipping shipping) {
        this.id = id;
        this.dateCreated = dateCreated;
        this.payment = payment;
        this.customer = customer;
        this.shipping = shipping;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Shipping getShipping() {
        return shipping;
    }

    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }
}
