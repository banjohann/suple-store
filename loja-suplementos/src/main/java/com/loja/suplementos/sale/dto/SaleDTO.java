package com.loja.suplementos.sale.dto;

import lombok.Data;

import java.util.List;

@Data
public class SaleDTO {
    private Long customerId;
    private Long shippingId;
    private Long paymentId;
    private List<ProductQuantityDTO> products;

    @Data
    public static class ProductQuantityDTO {
        private Long productId;
        private Integer quantity;
    }
}
