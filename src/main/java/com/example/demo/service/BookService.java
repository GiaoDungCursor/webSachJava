package com.example.demo.service;

import com.example.demo.model.Book;
import com.example.demo.model.Category;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    public BookService(BookRepository bookRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Book> listBooks(String category, String keyword) {
        List<Book> books;
        if (category != null && !category.isBlank()) {
            books = bookRepository.findByCategoryId(category);
        } else {
            books = bookRepository.findAll();
        }

        if (keyword != null && !keyword.isBlank()) {
            String lowerKeyword = keyword.toLowerCase();
            return books.stream()
                    .filter(b -> (b.getTitle() != null && b.getTitle().toLowerCase().contains(lowerKeyword)) ||
                            (b.getAuthor() != null && b.getAuthor().toLowerCase().contains(lowerKeyword)))
                    .collect(Collectors.toList());
        }
        return books;
    }

    public List<String> listCategories() {
        return categoryRepository.findAll().stream()
                .map(Category::getName)
                .collect(Collectors.toList());
    }

    public Optional<Book> findById(String id) {
        return bookRepository.findById(id);
    }

    public void adjustStock(String bookId, int delta) {
        findById(bookId).ifPresent(book -> {
            long currentStock = book.getStock() == null ? 0 : book.getStock();
            book.setStock(Math.max(0, currentStock + delta));
            bookRepository.save(book);
        });
    }

    public void addBook(Book book) {
        bookRepository.save(book);
    }

    public void deleteBook(String id) {
        bookRepository.deleteById(id);
    }

    public void updateBook(Book book) {
        // Ensure book exists before updating if necessary, or just save (upsert)
        // Ideally we should check if ID exists to avoid unintentional creates, but save
        // works for both.
        // For strict update semantics:
        if (bookRepository.existsById(book.getId())) {
            bookRepository.save(book);
        }
    }

    public long catalogSize() {
        return bookRepository.count();
    }
}
