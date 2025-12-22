package com.example.demo.controller;

import com.example.demo.model.Book;
import com.example.demo.service.BookService;
import com.example.demo.service.CartService;
import com.example.demo.model.CartItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class CatalogController {

    private final BookService bookService;
    private final CartService cartService;

    public CatalogController(BookService bookService, CartService cartService) {
        this.bookService = bookService;
        this.cartService = cartService;
    }

    @GetMapping("/")
    public String list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "4") int size,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String keyword,
            Model model,
            HttpSession session
    ) {
        List<Book> matched = bookService.listBooks(category, keyword);
        int total = matched.size();
        int normalizedPage = Math.max(1, Math.min(page, Math.max(1, (total + size - 1) / size)));
        int fromIndex = Math.min((normalizedPage - 1) * size, total);
        int toIndex = Math.min(fromIndex + size, total);
        List<Book> pageContent = matched.subList(fromIndex, toIndex);
        int totalPages = Math.max(1, (total + size - 1) / size);
        List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());

        model.addAttribute("books", pageContent);
        model.addAttribute("currentPage", normalizedPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("keyword", keyword);
        model.addAttribute("category", category);
        model.addAttribute("categories", bookService.listCategories());
        model.addAttribute("cartSize", cartService.getCart(session).size());
        model.addAttribute("totalBooks", bookService.catalogSize());
        return "index";
    }
}
