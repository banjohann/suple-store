package com.loja.suplementos.brand.repository;

import com.loja.suplementos.brand.domain.Brand;
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
public class BrandRepositoryPGSQL implements BrandRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Brand> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Brand.class, id));
    }

    @Override
    public List<Brand> findAll() {
        return entityManager.createQuery("SELECT b FROM Brand b", Brand.class).getResultList();
    }

    @Override
    public void save(Brand brand) {
        if (brand.getId() == null) {
            entityManager.persist(brand);
        } else {
            entityManager.merge(brand);
        }
    }

    @Override
    public void delete(Brand brand) {
        entityManager.remove(brand);
    }
}
