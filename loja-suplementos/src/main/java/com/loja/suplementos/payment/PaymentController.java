package com.loja.suplementos.payment;

import com.loja.suplementos.payment.domain.Payment;
import com.loja.suplementos.payment.domain.PaymentMethod;
import com.loja.suplementos.payment.domain.PaymentStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("payments", paymentService.findAll());
        return "payments/index";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("payment", paymentService.findById(id));
        return "payments/details";
    }

    @PostMapping()
    public String savePayment(Payment payment) {
        paymentService.save(payment);
        return "redirect:/payments";
    }

    @GetMapping("/new")
    public String newPaymentForm(Model model) {
        model.addAttribute("payment", new Payment());
        model.addAttribute("paymentMethods", PaymentMethod.values());
        model.addAttribute("paymentStatuses", PaymentStatus.values());
        return "payments/new";
    }

    @GetMapping("/edit/{id}")
    public String editPaymentForm(@PathVariable Long id, Model model) {
        model.addAttribute("payment", paymentService.findById(id));
        model.addAttribute("paymentMethods", PaymentMethod.values());
        model.addAttribute("paymentStatuses", PaymentStatus.values());
        return "payments/edit";
    }

    @PostMapping("/edit/{id}")
    public String updatePayment(@PathVariable Long id, Payment payment) {
        paymentService.update(id, payment);
        return "redirect:/payments";
    }

    @PostMapping("/delete/{id}")
    public String deletePayment(@PathVariable Long id) {
        paymentService.delete(id);
        return "redirect:/payments";
    }
}

