package com.loja.suplementos.address.repository;

import com.loja.suplementos.address.DeliveryAddress;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@Profile("Postgres")
public class DeliveryAddressRepositoryPGSQL implements DeliveryAddressRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(DeliveryAddress deliveryAddress) {
        this.entityManager.persist(deliveryAddress);
    }

    @Override
    public void update(DeliveryAddress deliveryAddress) {
        this.entityManager.persist(deliveryAddress);
    }

    @Override
    public void delete(DeliveryAddress deliveryAddress) {
        this.entityManager.remove(deliveryAddress);
    }

    @Override
    public Optional<DeliveryAddress> findById(Long id) {
        return Optional.ofNullable(this.entityManager.find(DeliveryAddress.class, id));
    }

    @Override
    public List<DeliveryAddress> findAll() {
        return this.entityManager.createQuery("SELECT d FROM DeliveryAddress d", DeliveryAddress.class).getResultList();
    }

    @Override
    public List<DeliveryAddress> findAllFromCustomer(Long customerId) {
        return this.entityManager.createQuery("SELECT d FROM DeliveryAddress d WHERE d.customerId = :customerId", DeliveryAddress.class)
                .setParameter("customerId", customerId)
                .getResultList();
    }
}
