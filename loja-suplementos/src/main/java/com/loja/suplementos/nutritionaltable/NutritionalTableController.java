package com.loja.suplementos.nutritionaltable;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/nutritional-tables")
public class NutritionalTableController {

    private final NutritionalTableService service;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("nutritionalTables", service.findAll());
        return "nutritional-tables/index";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable("id") long id, Model model) {
        model.addAttribute("nutritionalTable", service.findById(id));
        return "nutritional-tables/details";
    }

    @GetMapping("/new")
    public String newNutritionalTable(Model model) {
        return "nutritional-tables/new";
    }

    @GetMapping("/edit/{id}")
    public String editNutritionalTable(@PathVariable long id, Model model) {
        model.addAttribute("nutritionalTable", service.findById(id));
        return "nutritional-tables/edit";
    }
}
