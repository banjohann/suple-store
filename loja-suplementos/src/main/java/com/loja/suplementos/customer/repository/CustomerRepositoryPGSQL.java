package com.loja.suplementos.customer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.loja.suplementos.customer.domain.Customer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
@Transactional
@Profile("Postgres")
public class CustomerRepositoryPGSQL implements CustomerRepository {
    
    @PersistenceContext
    private EntityManager entityManager;

    public void save(Customer customer) {
        entityManager.persist(customer);
    }

    public Optional<Customer> findById(Long id) {
        Customer customer = entityManager.find(Customer.class, id);
        return Optional.ofNullable(customer);
    }

    public Customer findByEmailOrCpf(String email, String cpf) {
        return entityManager
            .createQuery("SELECT c FROM Customer c WHERE c.email = :email OR c.cpf = :cpf", Customer.class)
            .setParameter("email", email)
            .setParameter("cpf", cpf)
            .setMaxResults(1)
            .getResultList()
            .stream()
            .findFirst()
            .orElse(null);
    }

    public void update(Customer customer) {
        entityManager.merge(customer);
    }

    public void delete(Customer customer) {
        entityManager.remove(customer);
    }

    public List<Customer> findAll() {
        return entityManager.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
    }
}
