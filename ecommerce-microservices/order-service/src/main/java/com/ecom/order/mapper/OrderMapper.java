package com.ecom.order.mapper;

import com.ecom.order.dto.OrderRequestdto;
import com.ecom.order.dto.OrderResponseDto;
import com.ecom.order.entity.Order;
import org.springframework.stereotype.Component;


@Component
public class OrderMapper {

    public  Order toEntity(OrderRequestdto dto) {

        Order order = new Order();

        order.setSkuCode(dto.getSkuCode());
        order.setQuantity(dto.getQuantity());
        order.setPrice(dto.getPrice());

        return order;
    }
    public  OrderResponseDto toResponseDto(Order order) {

        return new OrderResponseDto(
                order.getOrderNumber(),
                order.getOrderStatus()
        );
    }
}