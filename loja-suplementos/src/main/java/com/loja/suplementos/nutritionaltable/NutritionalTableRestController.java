package com.loja.suplementos.nutritionaltable;

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
@RequestMapping("/api/nutritional-tables")
public class NutritionalTableRestController {

    private final NutritionalTableService service;

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody Map<String, String> data){
        try {
            this.service.save(data);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("errorMessage", e.getMessage()));
        }

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody Map<String, String> data){
        try {
            this.service.update(id, data);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("errorMessage", e.getMessage()));
        }

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id){
        try {
            this.service.delete(id);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("errorMessage", e.getMessage()));
        }

        return ResponseEntity.ok().build();
    }
}
