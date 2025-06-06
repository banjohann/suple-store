package com.loja.suplementos.customer.repository;

import java.util.List;
import java.util.Optional;

import com.loja.suplementos.customer.domain.Customer;

public interface CustomerRepository {
    
    void save(Customer customer);

    Optional<Customer> findById(Long id);

    void update(Customer customer);

    void delete(Customer customer);

    Customer findByEmailOrCpf(String email, String cpf);

    List<Customer> findAll();
}
