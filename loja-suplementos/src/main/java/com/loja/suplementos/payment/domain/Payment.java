package com.loja.suplementos.payment.domain;

import java.math.BigDecimal;
import java.sql.Date;

import com.loja.suplementos.sale.domain.Sale;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payment")
public class Payment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status = PaymentStatus.PENDING;

    private BigDecimal amount;

    private Date transactionDate;

    public String getDescription() {
        return String.format("%d, %s, %s", id, paymentMethod.getDescription(), status.getDescription());
    }

    public Payment(PaymentMethod paymentMethod, BigDecimal amount, Date transactionDate) {
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }
}
