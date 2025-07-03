package com.banjohann.lojasuplementos.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class SaleItem implements Serializable {

    Long id;

    Long saleId;

    Product product;

    int quantity;

    BigDecimal price;

    public SaleItem(Long id, Long saleId, Product product, int quantity, BigDecimal price) {
        this.id = id;
        this.saleId = saleId;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSaleId() {
        return saleId;
    }

    public void setSaleId(Long saleId) {
        this.saleId = saleId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
