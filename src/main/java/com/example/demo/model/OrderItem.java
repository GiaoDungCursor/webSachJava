package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ChiTietHoaDon")
public class OrderItem {

    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY) // Removed for manual ID
    @Column(name = "MaChiTietHD", nullable = false)
    private Long id;

    @Column(name = "MaSach")
    private String bookId;

    @Column(name = "SoLuongMua")
    private Integer quantity;

    @Column(name = "MaHoaDon")
    private Long orderId;

    public OrderItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
