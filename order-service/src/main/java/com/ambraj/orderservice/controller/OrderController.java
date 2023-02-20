package com.ambraj.orderservice.controller;

import com.ambraj.orderservice.dto.OrderRequest;
import com.ambraj.orderservice.entity.Order;
import com.ambraj.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
        String orderNumber = orderService.placeOrder(orderRequest);
        return "Order placed successfully with orderNumber: " + orderNumber;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

}
