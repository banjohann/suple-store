package com.banjohann.lojasuplementos.model;

import java.math.BigDecimal;

public class Product {
    Long id;

    String barcode;

    String name;

    String description;

    ProductType type;

    BigDecimal price;

    Brand brand;

    NutritionalTable nutritionalTable;

    int quantityInStock;

    public Product(Long id, String barcode, String name, String description, ProductType type, BigDecimal price, Brand brand, NutritionalTable nutritionalTable, int quantityInStock) {
        this.id = id;
        this.barcode = barcode;
        this.name = name;
        this.description = description;
        this.type = type;
        this.price = price;
        this.brand = brand;
        this.nutritionalTable = nutritionalTable;
        this.quantityInStock = quantityInStock;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public NutritionalTable getNutritionalTable() {
        return nutritionalTable;
    }

    public void setNutritionalTable(NutritionalTable nutritionalTable) {
        this.nutritionalTable = nutritionalTable;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }
}
