package com.loja.suplementos.shipping;

import com.loja.suplementos.shipping.domain.Shipping;
import com.loja.suplementos.shipping.repository.ShippingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class ShippingService {

    private final ShippingRepository shippingRepository;

    public List<Shipping> findAllWithoutSale() {
        return shippingRepository.findAllWithoutSale();
    }

    public List<Shipping> findAll() {
        return shippingRepository.findAll();
    }

    public Shipping findById(Long id) {
        return shippingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Entrega não encontrada com o ID: " + id));
    }

    public void save(Shipping shipping) {
        shippingRepository.save(shipping);
    }

    public void update(Shipping shipping) {
        shippingRepository.update(shipping);
    }

    public void delete(Long id) {
        Shipping shipping = findById(id);
        shippingRepository.delete(shipping);
    }
}
