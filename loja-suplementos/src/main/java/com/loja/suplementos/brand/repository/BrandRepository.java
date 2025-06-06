package com.loja.suplementos.brand.repository;

import com.loja.suplementos.brand.domain.Brand;

import java.util.List;
import java.util.Optional;

public interface BrandRepository {

    Optional<Brand> findById(Long id);

    List<Brand> findAll();

    void save(Brand brand);

    void delete(Brand brand);
}
