package com.loja.suplementos.sale;

import com.loja.suplementos.customer.CustomerService;
import com.loja.suplementos.payment.PaymentService;
import com.loja.suplementos.payment.domain.Payment;
import com.loja.suplementos.product.ProductService;
import com.loja.suplementos.sale.domain.Sale;
import com.loja.suplementos.sale.domain.SaleItem;
import com.loja.suplementos.sale.dto.SaleDTO;
import com.loja.suplementos.sale.repository.SaleRepository;
import com.loja.suplementos.shipping.ShippingService;
import com.loja.suplementos.shipping.domain.Shipping;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class SaleService {

    private final SaleRepository saleRepository;
    private final ShippingService shippingService;
    private final CustomerService customerService;
    private final ProductService productService;
    private final PaymentService paymentService;

    public List<Sale> findAll() {
        return saleRepository.findAll();
    }

    public Sale findById(long id) {
        return saleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Venda não encontrada"));
    }

    public void save(SaleDTO saleDTO) {
        var customer = customerService.findById(saleDTO.getCustomerId());
        var shipping = shippingService.findById(saleDTO.getShippingId());
        var payment = paymentService.findById(saleDTO.getPaymentId());

        var saleItems = saleDTO.getProducts().stream().map(productQuantityDTO -> {
            var product = productService.findByBarcode(productQuantityDTO.getBarcode());

            return SaleItem.builder()
                .product(product)
                .quantity(productQuantityDTO.getQuantity())
                .price(product.getPrice().setScale(2))
                .build();
        }).collect(Collectors.toSet());

        var totalAmount = saleItems.stream()
            .map(item -> item.getPrice().multiply(new BigDecimal(item.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        payment.setAmount(totalAmount);
        paymentService.save(payment);

        Sale sale = Sale.builder()
            .customer(customer)
            .dateCreated(new Date())
            .payment(payment)
            .shipping(shipping)
            .saleItems(saleItems)
            .build();

        saleRepository.save(sale);
    }

    public void update(long id, SaleDTO saleDTO) {
        var sale = findById(id);
        var customer = customerService.findById(saleDTO.getCustomerId());
        var shipping = shippingService.findById(saleDTO.getShippingId());
        var payment = paymentService.findById(saleDTO.getPaymentId());

        sale.setCustomer(customer);
        sale.setShipping(shipping);
        sale.setPayment(payment);

        var saleItems = saleDTO.getProducts().stream().map(productQuantityDTO -> {
            var product = productService.findByBarcode(productQuantityDTO.getBarcode());

            return SaleItem.builder()
                .product(product)
                .quantity(productQuantityDTO.getQuantity())
                .price(product.getPrice())
                .build();
        }).collect(Collectors.toSet());

        var totalAmount = saleItems.stream()
            .map(item -> item.getPrice().multiply(new BigDecimal(item.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        payment.setAmount(totalAmount);
        paymentService.save(payment);

        sale.getSaleItems().clear();
        sale.getSaleItems().addAll(saleItems);

        saleRepository.save(sale);
    }

    public void delete(long id) {
        var sale = findById(id);
        saleRepository.delete(sale);
    }
}
