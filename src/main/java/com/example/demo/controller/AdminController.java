package com.example.demo.controller;

import com.example.demo.model.Book;
import com.example.demo.model.Category;
import com.example.demo.model.OrderDTO;
import com.example.demo.model.User;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.BookService;
import com.example.demo.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final BookService bookService;
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final CategoryRepository categoryRepository;

    public AdminController(BookService bookService, OrderService orderService, OrderRepository orderRepository,
            CategoryRepository categoryRepository) {
        this.bookService = bookService;
        this.orderService = orderService;
        this.orderRepository = orderRepository;
        this.categoryRepository = categoryRepository;
    }

    private boolean isAdmin(HttpSession session) {
        User user = (User) session.getAttribute(AuthController.SESSION_USER_KEY);
        return user != null && user.isAdmin();
    }

    // --- DASHBOARD ---
    @GetMapping("")
    public String adminDashboard(HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }
        return "admin"; // New dashboard
    }

    // --- BOOKS & ORDERS ---
    @GetMapping("/books")
    public String adminBooks(Model model, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }
        List<Book> books = bookService.listBooks(null, null);
        model.addAttribute("books", books);
        model.addAttribute("categories", bookService.listCategories());
        return "admin_books";
    }

    @GetMapping("/orders")
    public String adminOrders(Model model, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }
        List<OrderDTO> orders = orderService.listOrderDTOs();
        model.addAttribute("orders", orders);
        return "admin_orders";
    }

    @PostMapping("/books/add")
    public String addBook(
            @RequestParam String title,
            @RequestParam String author,
            @RequestParam String category,
            @RequestParam Long price,
            @RequestParam Long stock,
            @RequestParam(value = "image", required = false) org.springframework.web.multipart.MultipartFile imageFile,
            HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        String coverUrl = "https://via.placeholder.com/300x180.png?text=Book";
        if (imageFile != null && !imageFile.isEmpty()) {
            coverUrl = saveImage(imageFile);
            if (coverUrl == null)
                coverUrl = "https://via.placeholder.com/300x180.png?text=Error";
        }

        Book book = new Book(
                UUID.randomUUID().toString(),
                title,
                stock,
                price,
                category,
                "Vol 1",
                coverUrl,
                LocalDateTime.now(),
                author);
        bookService.addBook(book);
        return "redirect:/admin/books";
    }

    @PostMapping("/books/delete")
    public String deleteBook(@RequestParam String bookId, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }
        bookService.deleteBook(bookId);
        return "redirect:/admin/books";
    }

    @PostMapping("/books/edit")
    public String editBook(
            @RequestParam String id,
            @RequestParam String title,
            @RequestParam String author,
            @RequestParam String category,
            @RequestParam Long price,
            @RequestParam Long stock,
            @RequestParam(value = "image", required = false) org.springframework.web.multipart.MultipartFile imageFile,
            HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        Book existing = bookService.findById(id).orElse(null);
        if (existing == null) {
            return "redirect:/admin/books";
        }

        String coverUrl = existing.getCoverUrl();
        if (imageFile != null && !imageFile.isEmpty()) {
            String newUrl = saveImage(imageFile);
            if (newUrl != null)
                coverUrl = newUrl;
        }

        existing.setTitle(title);
        existing.setAuthor(author);
        existing.setCategoryId(category);
        existing.setPrice(price);
        existing.setStock(stock);
        existing.setCoverUrl(coverUrl);

        bookService.updateBook(existing);
        return "redirect:/admin/books";
    }

    @PostMapping("/orders/status")
    public String updateOrderStatus(@RequestParam Long orderId, @RequestParam String status, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }
        orderService.updateStatus(orderId, status);
        return "redirect:/admin/orders";
    }

    @PostMapping("/orders/approve")
    public String approveOrder(@RequestParam Long orderId, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }
        orderService.approveOrder(orderId);
        return "redirect:/admin/orders";
    }

    @PostMapping("/orders/refund")
    public String refundOrder(@RequestParam Long orderId, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }
        orderService.refundOrder(orderId);
        return "redirect:/admin/orders";
    }

    // --- CATEGORIES ---
    @GetMapping("/categories")
    public String adminCategories(Model model, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin_categories";
    }

    @PostMapping("/categories/add")
    public String addCategory(@RequestParam("id_input") String id, @RequestParam String name, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }
        Category cat = new Category(id, name);
        categoryRepository.save(cat);
        return "redirect:/admin/categories";
    }

    @PostMapping("/categories/edit")
    public String editCategory(@RequestParam("id") String id, @RequestParam String name, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }
        categoryRepository.findById(id).ifPresent(cat -> {
            cat.setName(name);
            categoryRepository.save(cat);
        });
        return "redirect:/admin/categories";
    }

    @PostMapping("/categories/delete")
    public String deleteCategory(@RequestParam String id, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }
        categoryRepository.deleteById(id);
        return "redirect:/admin/categories";
    }

    // --- REVENUE STATS ---
    @GetMapping("/stats")
    public String adminStats(Model model, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }
        BigDecimal total = orderRepository.sumTotalByStatus("APPROVED");
        if (total == null)
            total = BigDecimal.ZERO;

        // Count pending
        long pending = orderRepository.countByStatus("PENDING");

        // Chart data
        List<Object[]> revenueData = orderRepository.getRevenueOverTime();

        model.addAttribute("totalRevenue", total);
        model.addAttribute("pendingOrdersCount", pending);
        model.addAttribute("pendingOrders", orderService.listPendingOrders());
        model.addAttribute("revenueData", revenueData);

        return "admin_stats";
    }

    private String saveImage(org.springframework.web.multipart.MultipartFile file) {
        try {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            String uploadDir = "d:/webSachJava/src/main/resources/static/image_sach/";
            java.nio.file.Path path = java.nio.file.Paths.get(uploadDir + fileName);
            java.nio.file.Files.copy(file.getInputStream(), path, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            return "image_sach/" + fileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
