package com.banjohann.lojasuplementos.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Sale implements Serializable {

    Long id;

    Date dateCreated;

    Payment payment;

    Customer customer;

    Shipping shipping;

    List<SaleItem> saleItems;

    public Sale(Long id, Date dateCreated, Payment payment, Customer customer, Shipping shipping, List<SaleItem> saleItems) {
        this.id = id;
        this.dateCreated = dateCreated;
        this.payment = payment;
        this.customer = customer;
        this.saleItems = saleItems;
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

    public List<SaleItem> getSaleItems() {
        return saleItems;
    }

    public void setSaleItems(List<SaleItem> saleItems) {
        this.saleItems = saleItems;
    }
}
