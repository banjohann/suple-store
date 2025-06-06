package com.loja.suplementos.nutritionaltable.repository;

import com.loja.suplementos.nutritionaltable.domain.NutritionalTable;

import java.util.List;
import java.util.Optional;

public interface NutritionalTableRepository {

    Optional<NutritionalTable> findById(Long nutritionalTableId);

    List<NutritionalTable> findAll();

    void save(NutritionalTable nutritionalTable);

    void delete(NutritionalTable nutritionalTable);

    void update(NutritionalTable nutritionalTable);
}
