package com.loja.suplementos.nutritionaltable;

import com.loja.suplementos.nutritionaltable.domain.NutritionalTable;
import com.loja.suplementos.nutritionaltable.repository.NutritionalTableRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class NutritionalTableService {

    private final NutritionalTableRepository repository;

    public List<NutritionalTable> findAll() {
        return repository.findAll();
    }

    public NutritionalTable findById(long id) {
        return this.repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Tabela nutricional não encontrada com o id: " + id));
    }

    public void save(Map<String, String> data) {
        NutritionalTable nutritionalTable = NutritionalTable.builder()
            .calories(Integer.parseInt(data.get("calories")))
            .carbsPerServing(Double.parseDouble(data.get("carbsPerServing")))
            .proteinPerServing(Double.parseDouble(data.get("proteinPerServing")))
            .fatPerServing(Double.parseDouble(data.get("fatPerServing")))
            .servingSize(Float.parseFloat(data.get("servingSize")))
            .description(data.get("description"))
            .build();

        this.repository.save(nutritionalTable);
    }

    public void update(long id, Map<String, String> data) {
        NutritionalTable existingNutritionalTable = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Tabela nutricional não encontrada com o id: " + id));

        existingNutritionalTable.setCalories(Integer.parseInt(data.get("calories")));
        existingNutritionalTable.setCarbsPerServing(Double.parseDouble(data.get("carbsPerServing")));
        existingNutritionalTable.setFatPerServing(Double.parseDouble(data.get("fatPerServing")));
        existingNutritionalTable.setProteinPerServing(Double.parseDouble(data.get("proteinPerServing")));
        existingNutritionalTable.setServingSize(Float.parseFloat(data.get("servingSize")));
        existingNutritionalTable.setDescription(data.get("description"));

        this.repository.save(existingNutritionalTable);
    }

    public void delete(long id) {
        NutritionalTable nutritionalTable = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Tabela nutricional não encontrada com o id: " + id));
        this.repository.delete(nutritionalTable);
    }
}
