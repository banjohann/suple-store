package com.banjohann.lojasuplementos.model;

import java.io.Serializable;

public class NutritionalTable implements Serializable {
    private Long id;

    private float servingSize;

    private int calories;

    private double fatPerServing;

    private double carbsPerServing;

    private double proteinPerServing;

    private String description;

    public NutritionalTable(Long id, float servingSize, int calories, double fatPerServing, double carbsPerServing, double proteinPerServing, String description) {
        this.id = id;
        this.servingSize = servingSize;
        this.calories = calories;
        this.fatPerServing = fatPerServing;
        this.carbsPerServing = carbsPerServing;
        this.proteinPerServing = proteinPerServing;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getServingSize() {
        return servingSize;
    }

    public void setServingSize(float servingSize) {
        this.servingSize = servingSize;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public double getCarbsPerServing() {
        return carbsPerServing;
    }

    public void setCarbsPerServing(double carbsPerServing) {
        this.carbsPerServing = carbsPerServing;
    }

    public double getFatPerServing() {
        return fatPerServing;
    }

    public void setFatPerServing(double fatPerServing) {
        this.fatPerServing = fatPerServing;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getProteinPerServing() {
        return proteinPerServing;
    }

    public void setProteinPerServing(double proteinPerServing) {
        this.proteinPerServing = proteinPerServing;
    }
}
