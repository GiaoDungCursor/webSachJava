package com.example.demo.repository;

import com.example.demo.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    List<Book> findByCategoryId(String categoryId);

    List<Book> findByTitleContainingIgnoreCase(String keyword);

    List<Book> findByAuthorContainingIgnoreCase(String keyword);
}
