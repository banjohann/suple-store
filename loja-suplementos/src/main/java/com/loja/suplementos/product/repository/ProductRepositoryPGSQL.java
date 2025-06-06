package com.loja.suplementos.product.repository;

import com.loja.suplementos.product.domain.Product;
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
public class ProductRepositoryPGSQL implements ProductRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Product product) {
        this.entityManager.persist(product);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(this.entityManager.find(Product.class, id));
    }

    @Override
    public void update(Product product) {
        this.entityManager.merge(product);
    }

    @Override
    public void delete(Product product) {
        this.entityManager.remove(product);
    }

    @Override
    public List<Product> findAll() {
        return this.entityManager.createQuery("SELECT p FROM Product p", Product.class).getResultList();
    }

    @Override
    public List<Product> findAllInStock() {
        return this.entityManager.createQuery("SELECT p FROM Product p WHERE p.quantityInStock > 0", Product.class).getResultList();
    }
}
