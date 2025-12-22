package com.example.demo.service;

import com.example.demo.model.CartItem;
import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import com.example.demo.model.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    private final List<Order> orders = Collections.synchronizedList(new ArrayList<>());

    public Order createOrder(User user, List<CartItem> cartItems) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            orderItems.add(new OrderItem(cartItem.getBookId(), cartItem.getTitle(), cartItem.getPrice(), cartItem.getQuantity()));
        }
        BigDecimal total = cartItems.stream()
                .map(CartItem::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Order order = new Order(UUID.randomUUID().toString(), user.getUsername(), orderItems, total, "PENDING", LocalDateTime.now());
        orders.add(order);
        return order;
    }

    public List<Order> listOrders() {
        return new ArrayList<>(orders);
    }

    public Optional<Order> findById(String id) {
        synchronized (orders) {
            return orders.stream().filter(order -> order.getId().equals(id)).findFirst();
        }
    }

    public void updateStatus(String orderId, String status) {
        findById(orderId).ifPresent(order -> order.setStatus(status));
    }
}
