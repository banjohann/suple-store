package com.loja.suplementos.product;

import com.loja.suplementos.brand.domain.Brand;
import com.loja.suplementos.nutritionaltable.domain.NutritionalTable;
import com.loja.suplementos.product.domain.Product;
import com.loja.suplementos.product.domain.ProductType;
import com.loja.suplementos.brand.repository.BrandRepository;
import com.loja.suplementos.nutritionaltable.repository.NutritionalTableRepository;
import com.loja.suplementos.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final NutritionalTableRepository nutritionalTableRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> findAllInStock() {
        return productRepository.findAllInStock();
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));
    }

    public void save(Map<String, String> data) {
        var brandId = Long.parseLong(data.get("brandId"));
        var nutritionalTableId = Long.parseLong(data.get("nutritionalTableId"));

        var brand = brandRepository.findById(brandId)
            .orElseThrow(() -> new IllegalArgumentException("Marca não encontrada"));

        var nutritionalTable = nutritionalTableRepository.findById(nutritionalTableId)
            .orElseThrow(() -> new IllegalArgumentException("Tabela nutricional não encontrada"));

        var product = Product
            .builder()
            .name(data.get("name"))
            .brand(brand)
            .nutritionalTable(nutritionalTable)
            .description(data.get("description"))
            .price(new BigDecimal(data.get("price")))
            .type(ProductType.valueOf(data.get("type")))
            .quantityInStock(Integer.parseInt(data.get("quantityInStock")))
            .build();

        productRepository.save(product);
    }

    public void delete(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

        productRepository.delete(product);
    }

    public void update(Long id, Map<String, String> data) {
        var brandId = Long.parseLong(data.get("brandId"));
        var nutritionalTableId = Long.parseLong(data.get("nutritionalTableId"));

        var brand = brandRepository.findById(brandId)
            .orElseThrow(() -> new IllegalArgumentException("Marca não encontrada"));

        var nutritionalTable = nutritionalTableRepository.findById(nutritionalTableId)
            .orElseThrow(() -> new IllegalArgumentException("Tabela nutricional não encontrada"));

        var product = productRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

        product.setName(data.get("name"));
        product.setBrand(brand);
        product.setNutritionalTable(nutritionalTable);
        product.setDescription(data.get("description"));
        product.setPrice(new BigDecimal(data.get("price")));
        product.setType(ProductType.valueOf(data.get("type")));
        product.setQuantityInStock(Integer.parseInt(data.get("quantityInStock")));

        productRepository.update(product);
    }

    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    public List<NutritionalTable> getAllNutritionalTables() {
        return nutritionalTableRepository.findAll();
    }
}
