package com.example.demo.service;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import java.security.SecureRandom;

@Service
public class CaptchaService {

    private static final String CAPTCHA_SESSION_KEY = "CAPTCHA_CODE";
    private final SecureRandom random = new SecureRandom();

    public String generate(HttpSession session) {
        String code = random.ints(3, 0, 10)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
        session.setAttribute(CAPTCHA_SESSION_KEY, code);
        return code;
    }

    public boolean validate(HttpSession session, String input) {
        if (input == null) {
            return false;
        }
        Object stored = session.getAttribute(CAPTCHA_SESSION_KEY);
        return stored != null && stored.toString().equals(input.trim());
    }
}
