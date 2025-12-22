package com.example.demo.model;

import java.math.BigDecimal;

public class OrderItem {

    private final String bookId;
    private final String title;
    private final BigDecimal price;
    private final int quantity;

    public OrderItem(String bookId, String title, BigDecimal price, int quantity) {
        this.bookId = bookId;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
    }

    public String getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal lineTotal() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }
}
