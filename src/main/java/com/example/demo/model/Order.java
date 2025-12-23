package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "hoadon")
public class Order {

    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY) // Removed for manual ID
    @Column(name = "MaHoaDon", nullable = false)
    private Long id;

    @Column(name = "makh")
    private Long customerId;

    @Column(name = "NgayMua")
    private LocalDateTime orderDate;

    @Column(name = "damua", length = 20)
    private String status; // PENDING, APPROVED, REFUNDED

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
