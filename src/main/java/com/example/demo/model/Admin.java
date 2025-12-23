package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "DangNhap")
public class Admin {

    @Id
    @Column(name = "tendn", nullable = false)
    private String username;

    @Column(name = "pass")
    private String password;

    @Column(name = "Quyen", nullable = false)
    private boolean role; // 0: user, 1: admin (or similar logic, mapping BIT to boolean)

    public Admin() {
    }

    public Admin(String username, String password, boolean role) {
        this.username = username;
        this.password = password;
        this.role = role;
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

    public boolean isRole() {
        return role;
    }

    public void setRole(boolean role) {
        this.role = role;
    }
}
