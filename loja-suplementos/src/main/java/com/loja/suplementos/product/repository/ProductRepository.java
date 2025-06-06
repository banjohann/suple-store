package com.loja.suplementos.product.repository;

import com.loja.suplementos.product.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    void save(Product product);

    Optional<Product> findById(Long id);

    void update(Product product);

    void delete(Product product);

    List<Product> findAll();

    List<Product> findAllInStock();
}
