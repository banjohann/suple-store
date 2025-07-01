package com.banjohann.lojasuplementos.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Customer implements Serializable {
    private Long id;

    private String name;

    private String lastName;

    private String email;

    private String phone;

    private String cpf;

    private Date birthDate;

    private List<DeliveryAddress> deliveryAddresses;

    public Customer(Long id, String name, String lastName, String email, String phone, String cpf, Date birthDate, List<DeliveryAddress> deliveryAddresses) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.cpf = cpf;
        this.birthDate = birthDate;
        this.deliveryAddresses = deliveryAddresses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public List<DeliveryAddress> getDeliveryAddresses() {
        return deliveryAddresses;
    }

    public void setDeliveryAddresses(List<DeliveryAddress> deliveryAddresses) {
        this.deliveryAddresses = deliveryAddresses;
    }
}
