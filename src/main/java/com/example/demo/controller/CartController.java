package com.example.demo.controller;

import com.example.demo.model.Book;
import com.example.demo.model.CartItem;
import com.example.demo.service.BookService;
import com.example.demo.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CartController {

    private final CartService cartService;
    private final BookService bookService;

    public CartController(CartService cartService, BookService bookService) {
        this.cartService = cartService;
        this.bookService = bookService;
    }

    @GetMapping("/cart")
    public String viewCart(Model model, HttpSession session) {
        List<CartItem> items = cartService.getCart(session);
        model.addAttribute("items", items);
        model.addAttribute("total", cartService.cartTotal(session));
        return "cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(
            @RequestParam String bookId,
            @RequestParam(defaultValue = "1") int qty,
            HttpSession session
    ) {
        bookService.findById(bookId).ifPresent(book -> cartService.addItem(session, book, qty));
        return "redirect:/cart";
    }

    @PostMapping("/cart/update")
    public String updateCart(
            @RequestParam String bookId,
            @RequestParam int quantity,
            HttpSession session
    ) {
        cartService.updateQuantity(session, bookId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/cart/clear")
    public String clear(HttpSession session) {
        cartService.clearCart(session);
        return "redirect:/cart";
    }
}
