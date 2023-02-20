package com.ambraj.orderservice.service;

import com.ambraj.orderservice.dto.InventoryResponse;
import com.ambraj.orderservice.dto.OrderItemDto;
import com.ambraj.orderservice.dto.OrderRequest;
import com.ambraj.orderservice.entity.Order;
import com.ambraj.orderservice.entity.OrderItem;
import com.ambraj.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;

    public String placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderItem> orderItemList = orderRequest.getOrderItemList().stream().map(this::mapToEntity).toList();
        order.setOrderItemList(orderItemList);

        List<String> skuCodes = order.getOrderItemList().stream()
                .map(OrderItem::getSkuCode)
                .toList();

        InventoryResponse[] inventoryResponseArr = webClient.get()
                .uri("http://localhost:8082/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();
        boolean isInStock = Arrays.stream(Objects.requireNonNull(inventoryResponseArr))
                .allMatch(InventoryResponse::isInStock);

        if(isInStock) {
            order = orderRepository.save(order);
        }else {
            throw new IllegalArgumentException("Out of stock!");
        }
        return order.getOrderNumber();
    }

    public List<Order> getAllOrders() {
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
