package com.loja.suplementos;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.List;

@Component
public class SequenceSynchronyzer {

    private final JdbcTemplate jdbcTemplate;

    public SequenceSynchronyzer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void syncSequences() {
        List<String> tables = List.of("customer", "delivery_address", "shipping", "sale", "product", "brand", "nutritional_table", "payment", "sale_item");

        for (String table : tables) {
            String sequenceName = getSequenceName(table);
            if (sequenceName != null) {
                jdbcTemplate.execute(String.format(
                    "SELECT setval('%s', (SELECT COALESCE(MAX(id), 1) FROM %s))",
                    sequenceName,
                    table
                ));
            }
        }
    }

    private String getSequenceName(String tableName) {
        try {
            return jdbcTemplate.queryForObject(
                String.format("SELECT pg_get_serial_sequence('%s', 'id')", tableName),
                String.class
            );
        } catch (Exception e) {
            System.err.println("Sequence not found for table: " + tableName);
            return null;
        }
    }
}
