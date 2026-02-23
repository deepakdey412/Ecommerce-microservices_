package com.ecom.order.service;

import com.ecom.order.dto.OrderRequestdto;
import com.ecom.order.dto.OrderResponseDto;
import com.ecom.order.entity.Order;
import com.ecom.order.mapper.OrderMapper;
import com.ecom.order.repository.OrderRepositiory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepositiory repository;
    private final OrderMapper  mapper;
    public OrderService(OrderRepositiory repository, OrderMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public OrderResponseDto placeOrder(OrderRequestdto dto){
        Order newOrder = mapper.toEntity(dto);
        newOrder.setOrderNumber(UUID.randomUUID().toString());
        newOrder.setOrderStatus("CREATED");
        Order savedOrder = repository.save(newOrder);
        return mapper.toResponseDto(savedOrder);
    }
}
