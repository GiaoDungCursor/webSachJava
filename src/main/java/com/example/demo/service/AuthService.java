package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.util.HashUtil;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthService {

    private final Map<String, User> accounts = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        accounts.put("admin", new User("admin", "admin@example.com", HashUtil.md5("admin123"), "ADMIN"));
        accounts.put("user", new User("user", "user@example.com", HashUtil.md5("user123"), "USER"));
    }

    public Optional<User> authenticate(String username, String rawPassword) {
        User user = accounts.get(username);
        if (user == null) {
            return Optional.empty();
        }
        if (user.getPasswordHash().equals(HashUtil.md5(rawPassword))) {
            return Optional.of(user);
        }
        return Optional.empty();
    }

    public boolean register(String username, String email, String password) {
        if (accounts.containsKey(username)) {
            return false;
        }
        accounts.put(username, new User(username, email, HashUtil.md5(password), "USER"));
        return true;
    }

    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(accounts.get(username));
    }
}
