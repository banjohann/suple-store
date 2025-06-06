package com.loja.suplementos.shipping;

import com.loja.suplementos.address.repository.DeliveryAddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/shipping")
public class ShippingController {

     private final ShippingService shippingService;
     private final DeliveryAddressRepository deliveryAddressRepository;

     @GetMapping
     public String index(Model model) {
         model.addAttribute("shippings", shippingService.findAll());
         return "shipping/index";
     }

     @GetMapping("/{id}")
     public String detail(@PathVariable Long id, Model model) {
         model.addAttribute("shipping", shippingService.findById(id));
         return "shipping/details";
     }

     @GetMapping("/new")
     public String newShippingForm(Model model) {
         model.addAttribute("deliveryAddresses", deliveryAddressRepository.findAll());
         return "shipping/new";
     }

     @GetMapping("/edit/{id}")
     public String editShippingForm(@PathVariable Long id, Model model) {
         model.addAttribute("shipping", shippingService.findById(id));
         model.addAttribute("deliveryAddresses", deliveryAddressRepository.findAll());
         return "shipping/edit";
     }
}
