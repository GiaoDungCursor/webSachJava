package com.example.demo.service;

import com.example.demo.model.CartItem;
import com.example.demo.model.Book;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class CartService {

    private final ConcurrentMap<String, List<CartItem>> carts = new ConcurrentHashMap<>();

    public List<CartItem> getCart(HttpSession session) {
        return carts.computeIfAbsent(session.getId(), id -> new ArrayList<>());
    }

    public void clearCart(HttpSession session) {
        carts.remove(session.getId());
    }

    public void addItem(HttpSession session, Book book, int quantity) {
        if (quantity <= 0) {
            return;
        }
        List<CartItem> cart = getCart(session);
        synchronized (cart) {
            CartItem existing = cart.stream().filter(item -> item.getBookId().equals(book.getId())).findFirst().orElse(null);
            if (existing == null) {
                cart.add(new CartItem(book.getId(), book.getTitle(), book.getPrice(), quantity));
            } else {
                existing.setQuantity(existing.getQuantity() + quantity);
            }
        }
    }

    public void updateQuantity(HttpSession session, String bookId, int quantity) {
        List<CartItem> cart = getCart(session);
        synchronized (cart) {
            cart.removeIf(item -> {
                if (!item.getBookId().equals(bookId)) {
                    return false;
                }
                if (quantity <= 0) {
                    return true;
                }
                item.setQuantity(quantity);
                return false;
            });
        }
    }

    public BigDecimal cartTotal(HttpSession session) {
        return getCart(session).stream()
                .map(CartItem::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
