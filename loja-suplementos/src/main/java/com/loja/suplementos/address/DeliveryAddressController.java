package com.loja.suplementos.address;

import com.loja.suplementos.customer.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/address")
public class DeliveryAddressController {

    private final DeliveryAddressService service;
    private final CustomerService customerService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("deliveryAddresses", service.findAll());

        return "addresses/index";
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("customers", customerService.findAll());

        return "addresses/new";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        var deliveryAddress = service.findById(id);
        model.addAttribute("address", deliveryAddress);
        model.addAttribute("customers", customerService.findAll());

        return "addresses/edit";
    }
}
