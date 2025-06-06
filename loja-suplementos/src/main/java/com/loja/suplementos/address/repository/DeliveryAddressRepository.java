package com.loja.suplementos.address.repository;

import com.loja.suplementos.address.DeliveryAddress;

import java.util.List;
import java.util.Optional;

public interface DeliveryAddressRepository {

    void save(DeliveryAddress deliveryAddress);

    void update(DeliveryAddress deliveryAddress);

    void delete(DeliveryAddress deliveryAddress);

    Optional<DeliveryAddress> findById(Long id);

    List<DeliveryAddress> findAll();

    List<DeliveryAddress> findAllFromCustomer(Long customerId);
}
