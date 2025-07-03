package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/")
    public String rootRedirect() {
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logoutRedirect() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}