package com.loja.suplementos.payment.repository;

import com.loja.suplementos.payment.domain.Payment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("Postgres")
public class PaymentRepositoryPGSQL implements PaymentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Payment> findAll() {
        return entityManager.createQuery("SELECT p FROM Payment p ORDER BY p.id DESC", Payment.class).getResultList();
    }

    @Override
    public Optional<Payment> findById(Long id) {
        Payment payment = entityManager.find(Payment.class, id);
        return Optional.ofNullable(payment);
    }

    @Override
    public void save(Payment payment) {
        entityManager.persist(payment);
    }

    @Override
    public void delete(Payment payment) {
        entityManager.remove(payment);
    }

    @Override
    public List<Payment> findAllWithoutSale() {
        return entityManager.createQuery("SELECT p FROM Payment p LEFT JOIN Sale s ON s.payment = p WHERE s.id IS NULL", Payment.class).getResultList();
    }
}
