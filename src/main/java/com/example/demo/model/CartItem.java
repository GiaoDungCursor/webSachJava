package com.example.demo.model;

import java.math.BigDecimal;
import java.util.Objects;

public class CartItem {

    private final String bookId;
    private final String title;
    private final BigDecimal price;
    private int quantity;

    public CartItem(String bookId, String title, BigDecimal price, int quantity) {
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

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getLineTotal() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartItem)) return false;
        CartItem cartItem = (CartItem) o;
        return bookId.equals(cartItem.bookId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId);
    }
}
