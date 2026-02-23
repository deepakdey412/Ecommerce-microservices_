package com.ecom.order.repository;

import com.ecom.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepositiory extends JpaRepository<Order, Long> {
}
