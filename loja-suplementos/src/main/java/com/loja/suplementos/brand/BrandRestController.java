package com.loja.suplementos.brand;

import com.loja.suplementos.brand.domain.Brand;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/api/brands")
public class BrandRestController {

    private final BrandService brandService;

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody Brand brand) {
        try {
            this.brandService.save(brand);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("errorMessage", e.getMessage()));
        }

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Map<String, String> data) {
        try {
            this.brandService.update(id, data);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("errorMessage", e.getMessage()));
        }

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        try {
            this.brandService.deleteById(id);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("errorMessage", e.getMessage()));
        }

        return ResponseEntity.ok().build();
    }
}
