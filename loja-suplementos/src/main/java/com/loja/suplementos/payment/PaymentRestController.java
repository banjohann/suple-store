package com.loja.suplementos.payment;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/payments")
public class PaymentRestController {

    private final PaymentService paymentService;

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePayment(@PathVariable Long id) {
        try {
            paymentService.delete(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("errorMessage", "Este pagamento não pode ser excluído pois está vinculado a uma venda, exclua a venda primeiro."));
        }

        return ResponseEntity.ok().build();
    }
}