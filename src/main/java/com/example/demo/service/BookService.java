package com.example.demo.service;

import com.example.demo.model.Book;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final List<Book> catalog = new ArrayList<>();

    @PostConstruct
    public void initCatalog() {
        catalog.add(new Book(UUID.randomUUID().toString(), "Clean Architecture", "Robert C. Martin", "Software", "A guide to writing maintainable Java apps.", new BigDecimal("45.00"), 12, "https://via.placeholder.com/300x180.png?text=Clean+Architecture"));
        catalog.add(new Book(UUID.randomUUID().toString(), "Domain-Driven Design", "Eric Evans", "Software", "Deep dive into business-centric modeling.", new BigDecimal("53.00"), 8, "https://via.placeholder.com/300x180.png?text=DDD"));
        catalog.add(new Book(UUID.randomUUID().toString(), "Cracking the Coding Interview", "Gayle Laakmann McDowell", "Interview", "189 programming questions for interviews.", new BigDecimal("38.00"), 20, "https://via.placeholder.com/300x180.png?text=Coding+Interview"));
        catalog.add(new Book(UUID.randomUUID().toString(), "The Pragmatic Programmer", "Andrew Hunt", "Software", "Production-ready thinking for developers.", new BigDecimal("39.99"), 5, "https://via.placeholder.com/300x180.png?text=Pragmatic+Programmer"));
        catalog.add(new Book(UUID.randomUUID().toString(), "Atomic Habits", "James Clear", "Self-Help", "Small habits compound into remarkable results.", new BigDecimal("22.50"), 15, "https://via.placeholder.com/300x180.png?text=Atomic+Habits"));
        catalog.add(new Book(UUID.randomUUID().toString(), "Thinking in Systems", "Donella Meadows", "Science", "Systems thinking for complex problems.", new BigDecimal("28.00"), 6, "https://via.placeholder.com/300x180.png?text=Thinking+in+Systems"));
        catalog.add(new Book(UUID.randomUUID().toString(), "Sapiens", "Yuval Noah Harari", "History", "A brief history of humankind.", new BigDecimal("35.00"), 10, "https://via.placeholder.com/300x180.png?text=Sapiens"));
        catalog.add(new Book(UUID.randomUUID().toString(), "Educated", "Tara Westover", "Memoir", "A memoir about family and education.", new BigDecimal("21.99"), 18, "https://via.placeholder.com/300x180.png?text=Educated"));
    }

    public List<Book> listBooks(String category, String keyword) {
        return catalog.stream()
                .filter(book -> category == null || category.isBlank() || book.getCategory().equalsIgnoreCase(category))
                .filter(book -> keyword == null || keyword.isBlank() || book.getTitle().toLowerCase().contains(keyword.toLowerCase()) || book.getAuthor().toLowerCase().contains(keyword.toLowerCase()))
                .sorted(Comparator.comparing(Book::getTitle))
                .collect(Collectors.toList());
    }

    public List<String> listCategories() {
        return catalog.stream()
                .map(Book::getCategory)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    public Optional<Book> findById(String id) {
        return catalog.stream().filter(book -> book.getId().equals(id)).findFirst();
    }

    public void adjustStock(String bookId, int delta) {
        findById(bookId).ifPresent(book -> book.setStock(Math.max(0, book.getStock() + delta)));
    }

    public void addBook(Book book) {
        catalog.add(book);
    }

    public int catalogSize() {
        return catalog.size();
    }
}
