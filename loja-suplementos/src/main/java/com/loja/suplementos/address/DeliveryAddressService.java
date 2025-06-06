package com.loja.suplementos.address;

import com.loja.suplementos.address.repository.DeliveryAddressRepository;
import com.loja.suplementos.customer.CustomerService;
import com.loja.suplementos.customer.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
@AllArgsConstructor
public class DeliveryAddressService {

    private final CustomerService customerService;
    private final CustomerRepository customerRepository;
    private final DeliveryAddressRepository deliveryAddressRepository;

    public List<DeliveryAddress> findAll() {
        return deliveryAddressRepository.findAll();
    }

    public List<DeliveryAddress> findByCustomer(Long customerId) {
        return deliveryAddressRepository.findAllFromCustomer(customerId);
    }

    public DeliveryAddress findById(Long id) {
        return deliveryAddressRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Endereço de entrega não encontrado com o ID: " + id));
    }

    public void save(Map<String, String> params) {
        var customer = customerService.findById(Long.parseLong(params.get("customerId")));

        var deliveryAddress = new DeliveryAddress(
            params.get("street"),
            params.get("number"),
            params.get("neighborhood"),
            params.get("city"),
            params.get("state"),
            params.get("zipCode"),
            customer.getId()
        );

        deliveryAddressRepository.save(deliveryAddress);
    }

    public void update(Long id, Map<String, String> params) {
        var customer = customerService.findById(Long.parseLong(params.get("customerId")));
        var deliveryAddress = findById(id);

        deliveryAddress.setStreet(params.get("street"));
        deliveryAddress.setNumber(params.get("number"));
        deliveryAddress.setNeighborhood(params.get("neighborhood"));
        deliveryAddress.setCity(params.get("city"));
        deliveryAddress.setState(params.get("state"));
        deliveryAddress.setZipCode(params.get("zipCode"));
        deliveryAddress.setCustomerId(customer.getId());

        deliveryAddressRepository.save(deliveryAddress);
    }

    public void delete(Long addressId) {
        var deliveryAddress = findById(addressId);
        try {
            deliveryAddressRepository.delete(deliveryAddress);
        } catch (Exception e) {
            throw new RuntimeException("Não pode excluir o endereço de entrega, pois ele está associado a uma entrega. Apague a entrega primeiro.");
        }
    }
}
