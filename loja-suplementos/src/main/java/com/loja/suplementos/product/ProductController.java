package com.loja.suplementos.product;

import com.loja.suplementos.product.domain.ProductType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("products", service.findAll());

        return "products/index";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        var product = service.findById(id);
        model.addAttribute("product", product);
        return "products/details";
    }

    @GetMapping("/new")
    public String newProductForm(Model model) {
        model.addAttribute("brands", service.getAllBrands());
        model.addAttribute("nutritionalTables", service.getAllNutritionalTables());
        model.addAttribute("productTypes", ProductType.values());
        return "products/new";
    }

    @GetMapping("/{id}/edit")
    public String editProductForm(@PathVariable Long id, Model model) {
        var product = service.findById(id);
        model.addAttribute("product", product);
        model.addAttribute("brands", service.getAllBrands());
        model.addAttribute("nutritionalTables", service.getAllNutritionalTables());
        model.addAttribute("productTypes", ProductType.values());
        return "products/edit";
    }
}
