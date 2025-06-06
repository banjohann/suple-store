package com.loja.suplementos.customer;

import com.loja.suplementos.address.DeliveryAddressService;
import com.loja.suplementos.customer.domain.Customer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService service;
    private final DeliveryAddressService deliveryAddressService;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("customers", service.findAll());

        return "customers/index";
    }

    @GetMapping("/{id}")
    public String getCustomerDetails(@PathVariable Long id, Model model) {
        Customer customer = service.findById(id);
        var addresses = deliveryAddressService.findByCustomer(id);
        model.addAttribute("customer", customer);
        model.addAttribute("addresses", addresses);
        return "customers/details";
    }

    @GetMapping("/new")
    public String newCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customers/create";
    }

    @GetMapping("/{id}/edit")
    public String editCustomerForm(@PathVariable Long id, Model model) {
        Customer customer = service.findById(id);
        model.addAttribute("customer", customer);

        return "customers/edit";
    }
}
