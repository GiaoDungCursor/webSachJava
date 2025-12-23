package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "sach")
public class Book {

    @Id
    @Column(name = "masach", nullable = false)
    private String id;

    @Column(name = "tensach")
    private String title;

    @Column(name = "soluong")
    private Long stock;

    @Column(name = "gia")
    private Long price; // Assuming BigInt in DB maps to Long, or could be BigDecimal if needed, but
                        // schema said bigint

    @Column(name = "maloai")
    private String categoryId;

    @Column(name = "sotap")
    private String volume;

    @Column(name = "anh")
    private String coverUrl;

    @Column(name = "NgayNhap")
    private LocalDateTime importedDate;

    @Column(name = "tacgia")
    private String author;

    public Book() {
    }

    public Book(String id, String title, Long stock, Long price, String categoryId, String volume, String coverUrl,
            LocalDateTime importedDate, String author) {
        this.id = id;
        this.title = title;
        this.stock = stock;
        this.price = price;
        this.categoryId = categoryId;
        this.volume = volume;
        this.coverUrl = coverUrl;
        this.importedDate = importedDate;
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public LocalDateTime getImportedDate() {
        return importedDate;
    }

    public void setImportedDate(LocalDateTime importedDate) {
        this.importedDate = importedDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
