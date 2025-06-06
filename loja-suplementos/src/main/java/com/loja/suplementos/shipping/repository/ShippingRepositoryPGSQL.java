package com.loja.suplementos.shipping.repository;

import com.loja.suplementos.shipping.domain.Shipping;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("Postgres")
public class ShippingRepositoryPGSQL implements ShippingRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Shipping shipping) {
        entityManager.persist(shipping);
    }

    @Override
    public void update(Shipping shipping) {
        entityManager.merge(shipping);
    }

    @Override
    public void delete(Shipping shipping) {
        entityManager.remove(shipping);
    }

    @Override
    public Optional<Shipping> findById(Long id) {
        Shipping shipping = entityManager.find(Shipping.class, id);
        return Optional.ofNullable(shipping);
    }

    @Override
    public List<Shipping> findAll() {
        return entityManager.createQuery("SELECT s FROM Shipping s", Shipping.class)
                .getResultList();
    }

    @Override
    public List<Shipping> findAllWithoutSale() {
        return entityManager.createQuery("SELECT s FROM Shipping s LEFT JOIN Sale sa ON sa.shipping = s WHERE sa.id IS NULL", Shipping.class)
                .getResultList();
    }
}
