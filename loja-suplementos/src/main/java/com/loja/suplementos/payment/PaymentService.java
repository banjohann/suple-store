package com.loja.suplementos.payment;

import com.loja.suplementos.payment.domain.Payment;
import com.loja.suplementos.payment.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public List<Payment> findAllWithouSale() {
        return paymentRepository.findAllWithoutSale();
    }

    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    public Payment findById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pagamento não encontrado"));
    }

    public void save(Payment payment) {
        paymentRepository.save(payment);
    }

    public void update(Long id, Payment updatedPayment) {
        var payment = findById(id);
        payment.setAmount(updatedPayment.getAmount());
        payment.setPaymentMethod(updatedPayment.getPaymentMethod());
        payment.setStatus(updatedPayment.getStatus());
        paymentRepository.save(payment);
    }

    public void delete(Long id) {
        var payment = findById(id);
        paymentRepository.delete(payment);
    }
}