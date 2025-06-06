package com.loja.suplementos.sale.repository;

import com.loja.suplementos.sale.domain.Sale;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Transactional
@Profile("Postgres")
public class SaleRepositoryPGSQL implements SaleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Sale> findById(long id) {
        Sale sale = entityManager.find(Sale.class, id);
        return Optional.ofNullable(sale);
    }

    @Override
    public List<Map<String, Object>> getCitiesWithMostSales() {
        String jpql = "SELECT da.city, COUNT(s.id) AS salesCount, SUM(si.price * si.quantity) AS totalSalesValue " +
            "FROM Sale s " +
            "JOIN s.shipping sh " +
            "JOIN sh.deliveryAddress da " +
            "JOIN s.saleItems si " +
            "GROUP BY da.city " +
            "ORDER BY salesCount DESC, totalSalesValue DESC";

        List<Object[]> results = entityManager.createQuery(jpql, Object[].class).getResultList();

        return results.stream().map(result -> {
            Map<String, Object> map = new HashMap<>();
            map.put("city", result[0]);
            map.put("salesCount", result[1]);
            map.put("totalSalesValue", result[2]);
            return map;
        }).toList();
    }

    @Override
    public List<Map<String, Object>> getClientsWithMostPurchaseValue() {
        String jpql = "SELECT c.name, c.lastName, COUNT(s.id) AS purchaseCount, SUM(si.price * si.quantity) AS totalPurchaseValue " +
            "FROM Sale s " +
            "JOIN s.customer c " +
            "JOIN s.saleItems si " +
            "GROUP BY c.id, c.name, c.lastName " +
            "ORDER BY totalPurchaseValue DESC";

        List<Object[]> results = entityManager.createQuery(jpql, Object[].class).getResultList();

        return results.stream().map(result -> {
            Map<String, Object> map = new HashMap<>();
            map.put("name", result[0]);
            map.put("lastName", result[1]);
            map.put("purchaseCount", result[2]);
            map.put("totalPurchaseValue", result[3]);
            return map;
        }).toList();
    }

    @Override
    public List<Map<String, Object>> getProductsWithMostSales() {
        String jpql = "SELECT p.name, COUNT(si.id) AS salesCount, SUM(si.price * si.quantity) AS totalSalesValue " +
            "FROM SaleItem si " +
            "JOIN si.product p " +
            "GROUP BY p.id, p.name " +
            "ORDER BY salesCount DESC, totalSalesValue DESC " +
            "LIMIT 20 ";

        List<Object[]> results = entityManager.createQuery(jpql, Object[].class).getResultList();

        return results.stream().map(result -> {
            Map<String, Object> map = new HashMap<>();
            map.put("productName", result[0]);
            map.put("salesCount", result[1]);
            map.put("totalSalesValue", result[2]);
            return map;
        }).toList();
    }

    @Override
    public List<Sale> findAll() {
        return entityManager.createQuery("SELECT s FROM Sale s", Sale.class).getResultList();
    }

    @Override
    public void save(Sale sale) {
        entityManager.persist(sale);
    }

    @Override
    public void update(Sale sale) {
        entityManager.merge(sale);
    }

    @Override
    public void delete(Sale sale) {
        entityManager.remove(entityManager.contains(sale) ? sale : entityManager.merge(sale));
    }
}
