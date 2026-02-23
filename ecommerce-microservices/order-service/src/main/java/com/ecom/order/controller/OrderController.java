package com.ecom.order.controller;

import com.ecom.order.dto.OrderRequestdto;
import com.ecom.order.dto.OrderResponseDto;
import com.ecom.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // ✅ Place Order
    @PostMapping
    public ResponseEntity<OrderResponseDto> placeOrder(@Valid @RequestBody OrderRequestdto dto) {

        OrderResponseDto response = orderService.placeOrder(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}