package com.loja.suplementos.sale.repository;

import com.loja.suplementos.sale.domain.SaleItem;

import java.util.List;
import java.util.Optional;

public interface SaleItemRepository {

    Optional<SaleItem> findById(long id);

    List<SaleItem> findAll();

    void save(SaleItem saleItem);

    void update(SaleItem saleItem);

    void delete(SaleItem saleItem);
}
