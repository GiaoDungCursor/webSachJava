package com.example.demo.controller;

import com.example.demo.model.CartItem;
import com.example.demo.model.Order;
import com.example.demo.model.User;
import com.example.demo.service.BookService;
import com.example.demo.service.CartService;
import com.example.demo.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Controller
public class CheckoutController {

    private final CartService cartService;
    private final OrderService orderService;
    private final BookService bookService;

    public CheckoutController(CartService cartService, OrderService orderService, BookService bookService) {
        this.cartService = cartService;
        this.orderService = orderService;
        this.bookService = bookService;
    }

    @GetMapping("/checkout")
    public String checkout(Model model, HttpSession session) {
        User user = (User) session.getAttribute(AuthController.SESSION_USER_KEY);
        if (user == null) {
            model.addAttribute("error", "Vui lòng đăng nhập trước khi thanh toán.");
            return "login";
        }
        List<CartItem> cart = cartService.getCart(session);
        model.addAttribute("items", cart);
        model.addAttribute("total", cartService.cartTotal(session));
        return "checkout";
    }

    @PostMapping("/checkout/pay")
    public String pay(Model model, HttpSession session) {
        User user = (User) session.getAttribute(AuthController.SESSION_USER_KEY);
        if (user == null) {
            model.addAttribute("error", "Vui lòng đăng nhập trước khi thanh toán.");
            return "login";
        }
        List<CartItem> cart = cartService.getCart(session);
        if (cart.isEmpty()) {
            model.addAttribute("error", "Giỏ hàng đang trống.");
            return "cart";
        }

        AtomicBoolean hasEnoughStock = new AtomicBoolean(true);
        cart.forEach(item -> bookService.findById(item.getBookId()).ifPresent(book -> {
            if (book.getStock() < item.getQuantity()) {
                hasEnoughStock.set(false);
            }
        }));

        if (!hasEnoughStock.get()) {
            model.addAttribute("error", "Số lượng trong kho không đủ, vui lòng kiểm tra lại.");
            model.addAttribute("items", cart);
            model.addAttribute("total", cartService.cartTotal(session));
            return "cart";
        }

        Order order = orderService.createOrder(user, cart);
        cart.forEach(item -> bookService.adjustStock(item.getBookId(), -item.getQuantity()));
        cartService.clearCart(session);

        model.addAttribute("order", order);
        return "confirmation";
    }
}
