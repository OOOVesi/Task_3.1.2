package ru.itmentor.spring.boot_security.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.RoleService;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String adminPage(Model model) {
        List<User> allUsers = userService.findAll();
        model.addAttribute("users", allUsers);
        return "admin";
    }

    @GetMapping("/add")
    public String addUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.findAll());
        return "user-form";
    }

    @PostMapping("/add")
    public String saveUser(@ModelAttribute User user,
                           @RequestParam(name = "roleNames") Set<String> roleNames) {
        userService.saveUser(user, roleNames);
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        User user = userService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id: " + id));
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleService.findAll());
        return "user-form";
    }

    @PostMapping("/edit")
    public String updateUser(@ModelAttribute User user,
                             @RequestParam(name = "roleNames") Set<String> roleNames) {

        User existing = userService.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        boolean wasAdmin = existing.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ROLE_ADMIN"));
        boolean willBeAdmin = roleNames.contains("ROLE_ADMIN");

        if (wasAdmin && !willBeAdmin) {
            throw new SecurityException("Нельзя снять роль ADMIN у администратора!");
        }

        userService.saveUser(user, roleNames);
        return "redirect:/admin";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {

        User user = userService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        boolean isAdmin = user.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ROLE_ADMIN"));

        if (isAdmin) {
            System.out.println(">>> ⚠ Попытка удалить администратора отклонена!");
            throw new SecurityException("Нельзя удалить пользователя с ролью ADMIN!");
        }

        userService.deleteById(id);
        return "redirect:/admin";
    }
}
