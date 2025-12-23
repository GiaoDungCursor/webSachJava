package com.example.demo.service;

import com.example.demo.model.CartItem;
import com.example.demo.model.Customer;
import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import com.example.demo.model.User;
import com.example.demo.model.OrderDTO;
import com.example.demo.model.Book;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.OrderDetailRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final CustomerRepository customerRepository;
    private final BookRepository bookRepository;

    public OrderService(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository,
            CustomerRepository customerRepository, BookRepository bookRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.customerRepository = customerRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public Order createOrder(User user, List<CartItem> cartItems) {
        // Find customer by username implementation detail:
        // We're assuming the logged-in User's username corresponds to Customer's
        // username or email or we need to look it up.
        // For simplicity now, we try to find by username.
        Optional<Customer> customerOpt = customerRepository.findByUsername(user.getUsername());
        Long customerId = customerOpt.map(Customer::getId).orElse(null);

        // If no customer found (maybe admin buying? or guest?), handle appropriately.
        // For now, allow null or throw exception. Let's allow null for simplicity if
        // logic permits,
        // but foreign key might fail. Real app should handle this.

        Order order = new Order();
        order.setCustomerId(customerId);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING"); // Default to PENDING status.

        // Manually generate Order ID
        Long maxOrderId = orderRepository.findMaxId();
        order.setId((maxOrderId == null) ? 1L : maxOrderId + 1);

        order = orderRepository.save(order);

        // Manually generate OrderItem IDs
        Long maxItemId = orderDetailRepository.findMaxId();
        long currentItemId = (maxItemId == null) ? 0L : maxItemId;

        for (CartItem cartItem : cartItems) {
            currentItemId++; // Increment ID for each item
            OrderItem item = new OrderItem();
            item.setId(currentItemId);
            item.setOrderId(order.getId());
            item.setBookId(cartItem.getBookId());
            item.setQuantity(cartItem.getQuantity());
            // Note: DB schema for ChiTietHoaDon doesn't seem to have Price, just Qty.
            // We just save Qty. Price is looked up from Book at runtime for Total calc?
            // Ideally we should snapshot price, but schema doesn't have it.

            orderDetailRepository.save(item);
        }

        return order;
    }

    public List<Order> listOrders() {
        return orderRepository.findAll();
    }

    public List<OrderDTO> listOrderDTOs() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<OrderDTO> listPendingOrders() {
        return orderRepository.findByStatus("PENDING").stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private OrderDTO convertToDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(order.getStatus()); // Use status String directly

        // Username
        if (order.getCustomerId() != null) {
            customerRepository.findById(order.getCustomerId())
                    .ifPresent(c -> dto.setUsername(c.getUsername()));
        } else {
            dto.setUsername("Guest");
        }

        // Total
        List<OrderItem> items = orderDetailRepository.findByOrderId(order.getId());
        long totalVal = 0;
        for (OrderItem item : items) {
            Optional<Book> bookOpt = bookRepository.findById(item.getBookId());
            if (bookOpt.isPresent()) {
                long price = bookOpt.get().getPrice() != null ? bookOpt.get().getPrice() : 0;
                totalVal += price * item.getQuantity();
            }
        }
        dto.setTotal(BigDecimal.valueOf(totalVal));

        return dto;
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public void updateStatus(Long orderId, String status) {
        findById(orderId).ifPresent(order -> {
            order.setStatus(status);
            orderRepository.save(order);
        });
    }

    @Transactional
    public void approveOrder(Long orderId) {
        findById(orderId).ifPresent(order -> {
            if ("PENDING".equals(order.getStatus())) {
                order.setStatus("APPROVED");
                orderRepository.save(order);
            }
        });
    }

    @Transactional
    public void refundOrder(Long orderId) {
        findById(orderId).ifPresent(order -> {
            // Only refund if not already refunded
            if (!"REFUNDED".equals(order.getStatus())) {
                // Restore stock
                List<OrderItem> items = orderDetailRepository.findByOrderId(order.getId());
                for (OrderItem item : items) {
                    bookRepository.findById(item.getBookId()).ifPresent(book -> {
                        book.setStock(book.getStock() + item.getQuantity());
                        bookRepository.save(book);
                    });
                }
                order.setStatus("REFUNDED");
                orderRepository.save(order);
            }
        });
    }
}
