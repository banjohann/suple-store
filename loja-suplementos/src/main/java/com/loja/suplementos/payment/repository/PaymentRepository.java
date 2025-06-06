package com.loja.suplementos.payment.repository;

import com.loja.suplementos.payment.domain.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository {

    void save(Payment payment);

    void delete(Payment payment);

    Optional<Payment> findById(Long id);

    List<Payment> findAll();

    List<Payment> findAllWithoutSale();
}
