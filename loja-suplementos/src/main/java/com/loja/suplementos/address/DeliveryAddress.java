package com.loja.suplementos.address;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "delivery_address")
public class DeliveryAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;

    private String street;

    private String number;

    private String neighborhood;

    private String city;

    private String state;

    private String zipCode;

    public DeliveryAddress(String street, String number, String neighborhood, String city, String state, String zipCode, Long customerId) {
        this.street = street;
        this.number = number;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.customerId = customerId;
    }

    public String getFullAddress() {
        return street + ", " + number + ", " + neighborhood + ", " + city + ", " + state + ", " + zipCode;
    }
}
