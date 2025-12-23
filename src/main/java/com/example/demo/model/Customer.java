package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "KhachHang")
public class Customer {

    @Id
    @Column(name = "makh", nullable = false)
    private Long id;

    @Column(name = "hoten")
    private String fullName;

    @Column(name = "diachi")
    private String address;

    @Column(name = "sodt")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "tendn")
    private String username;

    @Column(name = "pass")
    private String password;

    public Customer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
