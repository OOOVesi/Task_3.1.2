package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itmentor.spring.boot_security.demo.model.User;

@Controller
public class UserController {

    @GetMapping("/user")
    public String userPage(@AuthenticationPrincipal User currentUser, Model model) {
        model.addAttribute("user", currentUser);
        return "user";
    }
}
