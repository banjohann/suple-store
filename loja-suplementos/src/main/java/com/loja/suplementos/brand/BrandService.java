package com.loja.suplementos.brand;

import com.loja.suplementos.brand.domain.Brand;
import com.loja.suplementos.brand.repository.BrandRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;

    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    public Brand findById(long id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Marca não encontrada com o id: " + id));
    }

    public void save(Brand brand) {
        brandRepository.save(brand);
    }

    public void update(long id, Map<String, String> data) {
        Brand existingBrand = findById(id);

        existingBrand.setName(data.get("name"));
        existingBrand.setShortName(data.get("shortName"));

        brandRepository.save(existingBrand);
    }

    public void deleteById(long id) {
        Brand brand = findById(id);

        brandRepository.delete(brand);
    }
}
