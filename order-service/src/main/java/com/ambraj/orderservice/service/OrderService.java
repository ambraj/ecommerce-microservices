package com.ambraj.orderservice.service;

import com.ambraj.orderservice.dto.OrderItemDto;
import com.ambraj.orderservice.dto.OrderRequest;
import com.ambraj.orderservice.entity.Order;
import com.ambraj.orderservice.entity.OrderItem;
import com.ambraj.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public String placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderItem> orderItemList = orderRequest.getOrderItemList().stream().map(this::mapToEntity).toList();
        order.setOrderItemList(orderItemList);

        order = orderRepository.save(order);
        return order.getOrderNumber();
    }

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    private OrderItem mapToEntity(OrderItemDto orderItemDto) {
        return OrderItem.builder()
                .skuCode(orderItemDto.getSkuCode())
                .price(orderItemDto.getPrice())
                .quantity(orderItemDto.getQuantity())
                .build();
    }

}
