package com.loja.suplementos.shipping.repository;

import com.loja.suplementos.shipping.domain.Shipping;

import java.util.List;
import java.util.Optional;

public interface ShippingRepository {

    void save(Shipping shipping);

    void update(Shipping shipping);

    void delete(Shipping shipping);

    Optional<Shipping> findById(Long id);

    List<Shipping> findAll();

    List<Shipping> findAllWithoutSale();
}
