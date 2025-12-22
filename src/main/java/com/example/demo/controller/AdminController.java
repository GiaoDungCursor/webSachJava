package com.example.demo.controller;

import com.example.demo.model.Book;
import com.example.demo.model.Order;
import com.example.demo.model.User;
import com.example.demo.service.BookService;
import com.example.demo.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Controller
public class AdminController {

    private final BookService bookService;
    private final OrderService orderService;

    public AdminController(BookService bookService, OrderService orderService) {
        this.bookService = bookService;
        this.orderService = orderService;
    }

    private boolean isAdmin(HttpSession session) {
        User user = (User) session.getAttribute(AuthController.SESSION_USER_KEY);
        return user != null && user.isAdmin();
    }

    @GetMapping("/admin")
    public String adminPage(Model model, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }
        List<Book> books = bookService.listBooks(null, null);
        List<Order> orders = orderService.listOrders();
        model.addAttribute("books", books);
        model.addAttribute("orders", orders);
        model.addAttribute("categories", bookService.listCategories());
        return "admin";
    }

    @PostMapping("/admin/books/add")
    public String addBook(
            @RequestParam String title,
            @RequestParam String author,
            @RequestParam String category,
            @RequestParam String description,
            @RequestParam BigDecimal price,
            @RequestParam int stock
            , HttpSession session
    ) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }
        Book book = new Book(UUID.randomUUID().toString(), title, author, category, description, price, stock, "https://via.placeholder.com/300x180.png?text=Book");
        bookService.addBook(book);
        return "redirect:/admin";
    }

    @PostMapping("/admin/orders/status")
    public String updateOrderStatus(@RequestParam String orderId, @RequestParam String status, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }
        orderService.updateStatus(orderId, status);
        return "redirect:/admin";
    }
}
