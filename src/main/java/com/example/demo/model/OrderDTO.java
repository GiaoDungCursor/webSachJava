package com.example.demo.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderDTO {
    private Long id;
    private String username;
    private BigDecimal total;
    private String status;
    private LocalDateTime orderDate;

    public OrderDTO() {
    }

    public OrderDTO(Long id, String username, BigDecimal total, String status, LocalDateTime orderDate) {
        this.id = id;
        this.username = username;
        this.total = total;
        this.status = status;
        this.orderDate = orderDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
}
