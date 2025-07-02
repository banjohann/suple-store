package com.loja.suplementos.customer;

import com.loja.suplementos.customer.domain.Customer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/customers")
public class CustomerRestController {

    private final CustomerService service;

    @GetMapping("")
    public ResponseEntity<?> getAllCustomers() {
        try {
            return ResponseEntity.ok(service.findAll());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("errorMessage", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.findById(id));
        } catch (DataIntegrityViolationException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("errorMessage", e.getMessage()));
        }
    }

    @PostMapping("/app")
    public ResponseEntity<?> save(@RequestBody Customer customer) {
        try {
            Customer response = this.service.save(customer);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("errorMessage", e.getMessage()));
        }
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody Map<String, String> data) {
        try {
            this.service.save(data);
        } catch (Exception e) {
            log.error(e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("errorMessage", e.getMessage()));
        }

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editCustomer(@PathVariable Long id, @RequestBody Map<String, String> data) {
        try {
            this.service.update(data);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("errorMessage", e.getMessage()));
        }

        return ResponseEntity.ok().build();
    }

    @PutMapping("/app/{id}")
    public ResponseEntity<?> editCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        try {
            this.service.update(id, customer);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("errorMessage", e.getMessage()));
        }

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        try {
            this.service.delete(id);
        } catch (Throwable e) {
            var errorMessage = "Não é possível excluir o cliente, pois ele está vinculado a um pedido";

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("errorMessage", errorMessage));
        }

        return ResponseEntity.ok().build();
    }
}
