package com.ecom.order.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_number", nullable = false, unique = true)
    private String orderNumber;

    @Column(name = "sku_code", nullable = false)
    private String skuCode;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "order_status", nullable = false)
    private String orderStatus;

    // 🔹 Default Constructor (JPA requirement)
    public Order() {
    }

    // 🔹 All Args Constructor
    public Order(Long id, String orderNumber, String skuCode,
                 Integer quantity, BigDecimal price, String orderStatus) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.skuCode = skuCode;
        this.quantity = quantity;
        this.price = price;
        this.orderStatus = orderStatus;
    }

    // 🔹 Getters and Setters

    public Long getId() {
        return id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}