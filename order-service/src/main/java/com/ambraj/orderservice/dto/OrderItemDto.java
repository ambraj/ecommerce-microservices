package com.ambraj.orderservice.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderItemDto {
    private String skuCode;
    private BigDecimal price;
    private long quantity;
}
