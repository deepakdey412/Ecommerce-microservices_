package com.ecom.order.service;

import com.ecom.order.client.InventoryFeignClient;
import com.ecom.order.dto.InventoryResponse;
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
    private final InventoryFeignClient inventoryFeignClient;
    public OrderService(OrderRepositiory repository, OrderMapper mapper, InventoryFeignClient inventoryFeignClient) {
        this.repository = repository;
        this.mapper = mapper;
        this.inventoryFeignClient = inventoryFeignClient;
    }

    public OrderResponseDto placeOrder(OrderRequestdto dto){
        InventoryResponse inventoryResponse = inventoryFeignClient.inInStock(dto.getSkuCode());
        if(!inventoryResponse.isInStock()){
            throw new RuntimeException("Product is out of stock");
        }
        Order newOrder = mapper.toEntity(dto);
        newOrder.setOrderNumber(UUID.randomUUID().toString());
        newOrder.setOrderStatus("CREATED");
        Order savedOrder = repository.save(newOrder);
        return mapper.toResponseDto(savedOrder);
    }
}
