package com.loja.suplementos.sale;

import com.loja.suplementos.customer.CustomerService;
import com.loja.suplementos.payment.PaymentService;
import com.loja.suplementos.product.ProductService;
import com.loja.suplementos.sale.domain.Sale;
import com.loja.suplementos.shipping.ShippingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/sales")
public class SaleController {

    private SaleService service;
    private ProductService productService;
    private CustomerService customerService;
    private ShippingService shippingService;
    private PaymentService paymentService;

    @GetMapping()
    public String index(Model model) {
        var sales = service.findAll();
        model.addAttribute("sales", sales);

        var prices = sales.stream()
                .collect(Collectors.toMap(
                    Sale::getId,
                    sale -> sale.getSaleItems().stream()
                        .map(item -> item.getPrice().multiply(new BigDecimal(item.getQuantity())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                ));
        model.addAttribute("prices", prices);

        return "sales/index";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        var sale = service.findById(id);
        var totalPrice = sale.getSaleItems().stream()
                .map(item -> item.getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("sale", sale);
        model.addAttribute("totalPrice", totalPrice);
        return "sales/details";
    }

    @GetMapping("/new")
    public String newProductForm(Model model) {
        var customers = customerService.findAll();
        var shippings = shippingService.findAllWithoutSale();
        var payments = paymentService.findAllWithouSale();

        model.addAttribute("customers", customers);
        model.addAttribute("shippings", shippings);
        model.addAttribute("payments", payments);
        model.addAttribute("products", productService.findAllInStock());

        return "sales/new";
    }

    @GetMapping("/edit/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        var sale = service.findById(id);
        var customers = customerService.findAll();
        var shippings = shippingService.findAllWithoutSale();
        var payments = paymentService.findAllWithouSale();

        model.addAttribute("sale", sale);
        model.addAttribute("customers", customers);
        model.addAttribute("products", productService.findAllInStock());
        model.addAttribute("payments", payments);
        model.addAttribute("shippings", shippings);

        return "sales/edit";
    }
}
