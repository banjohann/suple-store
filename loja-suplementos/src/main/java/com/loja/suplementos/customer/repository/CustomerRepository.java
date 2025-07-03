package com.loja.suplementos.customer.repository;

import java.util.Optional;

import com.loja.suplementos.customer.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findById(Long id);

    Customer findByEmailOrCpf(String email, String cpf);

}
