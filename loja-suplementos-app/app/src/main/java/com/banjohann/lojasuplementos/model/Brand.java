package com.banjohann.lojasuplementos.model;

import java.io.Serializable;

public class Brand implements Serializable {

    Long id;

    String shortName;

    String name;

    public Brand(String name, String shortName) {
        this.name = name;
        this.shortName = shortName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
