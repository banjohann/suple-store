package com.loja.suplementos.sale.repository;

import com.loja.suplementos.sale.domain.SaleItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@Profile("postgres")
public class SaleItemRepositoryPGSQL implements SaleItemRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<SaleItem> findById(long id) {
        SaleItem saleItem = entityManager.find(SaleItem.class, id);
        return Optional.ofNullable(saleItem);
    }

    @Override
    public List<SaleItem> findAll() {
        return entityManager.createQuery("SELECT s FROM Sale s", SaleItem.class).getResultList();
    }

    @Override
    public void save(SaleItem saleItem) {
        entityManager.persist(saleItem);
    }

    @Override
    public void update(SaleItem saleItem) {
        entityManager.merge(saleItem);
    }

    @Override
    public void delete(SaleItem saleItem) {
        entityManager.remove(saleItem);
    }
}
