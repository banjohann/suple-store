package com.banjohann.lojasuplementos.model;

public enum ProductType {
    WHEY("Whey Protein"),
    CREATINE("Creatina"),
    PRE_WORKOUT("Pré-Treino"),
    VEGAN_PROTEIN("Proteína Vegana");

    private final String description;

    ProductType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
