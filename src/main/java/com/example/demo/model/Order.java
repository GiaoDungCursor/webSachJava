package com.example.demo.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Order {

    private final String id;
    private final String username;
    private final List<OrderItem> items;
    private final BigDecimal total;
    private String status;
    private final LocalDateTime createdAt;

    public Order(String id, String username, List<OrderItem> items, BigDecimal total, String status, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.items = items;
        this.total = total;
        this.status = status;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
