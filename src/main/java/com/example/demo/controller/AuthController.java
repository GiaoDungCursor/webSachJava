package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.AuthService;
import com.example.demo.service.CaptchaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    private final AuthService authService;
    private final CaptchaService captchaService;

    public static final String SESSION_USER_KEY = "LOGGED_IN_USER";

    public AuthController(AuthService authService, CaptchaService captchaService) {
        this.authService = authService;
        this.captchaService = captchaService;
    }

    @GetMapping("/login")
    public String loginForm(Model model, @RequestParam(required = false) String error) {
        model.addAttribute("error", error);
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            Model model
    ) {
        return authService.authenticate(username, password)
                .map(user -> {
                    session.setAttribute(SESSION_USER_KEY, user);
                    return "redirect:/";
                })
                .orElseGet(() -> {
                    model.addAttribute("error", "Sai tên người dùng hoặc mật khẩu.");
                    return "login";
                });
    }

    @GetMapping("/register")
    public String registerForm(Model model, HttpSession session) {
        model.addAttribute("captcha", captchaService.generate(session));
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String captcha,
            HttpSession session,
            Model model
    ) {
        if (!captchaService.validate(session, captcha)) {
            model.addAttribute("error", "Captcha không đúng.");
            model.addAttribute("captcha", captchaService.generate(session));
            return "register";
        }

        boolean created = authService.register(username, email, password);
        if (!created) {
            model.addAttribute("error", "Tên người dùng đã tồn tại.");
            model.addAttribute("captcha", captchaService.generate(session));
            return "register";
        }
        model.addAttribute("success", "Đăng ký thành công. Vui lòng đăng nhập.");
        return "login";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(SESSION_USER_KEY);
        return "redirect:/";
    }
}
