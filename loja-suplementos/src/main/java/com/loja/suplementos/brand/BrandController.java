package com.loja.suplementos.brand;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/brands")
public class BrandController {

    private final BrandService brandService;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("brands", brandService.findAll());
        return "brands/index";
    }

    @GetMapping("/new")
    public String newBrandForm() {
        return "brands/new";
    }

    @GetMapping("/{id}/edit")
    public String editBrandForm(@PathVariable Long id, Model model) {
        var brand = brandService.findById(id);
        model.addAttribute("brand", brand);
        return "brands/edit";
    }
}
