package com.ecom.order.mapper;

import com.ecom.order.dto.OrderRequestdto;
import com.ecom.order.dto.OrderResponseDto;
import com.ecom.order.entity.Order;

import java.util.UUID;

public class OrderMapper {

    public static Order toEntity(OrderRequestdto dto) {

        Order order = new Order();

        order.setSkuCode(dto.getSkuCode());
        order.setQuantity(dto.getQuantity());
        order.setPrice(dto.getPrice());

        return order;
    }
    public static OrderResponseDto toResponseDto(Order order) {

        return new OrderResponseDto(
                order.getOrderNumber(),
                order.getOrderStatus()
        );
    }
}