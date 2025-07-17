package ru.itmentor.spring.boot_security.demo.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.dto.UserResponseDto;
import ru.itmentor.spring.boot_security.demo.dto.UserUpdateDto;
import ru.itmentor.spring.boot_security.demo.mapper.UserMapper;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.RoleService;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final UserMapper userMapper;

    public AdminController(UserService userService, RoleService roleService, UserMapper userMapper) {
        this.userService = userService;
        this.roleService = roleService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public ResponseEntity<String> adminInfo() {
        return ResponseEntity.ok("{\"message\": \"Hello, Admin!\"}");
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<User> allUsers = userService.findAll();
        List<UserResponseDto> usersDto = allUsers.stream()
                .map(userMapper::toResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(usersDto);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        return userService.findById(id)
                .map(user -> ResponseEntity.ok(userMapper.toResponseDto(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/users")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserUpdateDto userUpdateDto) {
        User user = userMapper.toUser(userUpdateDto);
        User savedUser = userService.saveUser(user, userUpdateDto.getRoles());
        return ResponseEntity.ok(userMapper.toResponseDto(savedUser));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id,
                                                      @RequestBody UserUpdateDto userUpdateDto) {
        User existing = userService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        boolean wasAdmin = existing.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ROLE_ADMIN"));
        boolean willBeAdmin = userUpdateDto.getRoles().contains("ROLE_ADMIN");

        if (wasAdmin && !willBeAdmin) {
            throw new SecurityException("Нельзя снять роль ADMIN у администратора!");
        }

        userUpdateDto.setId(id);
        User userToSave = userMapper.toUser(userUpdateDto);
        User updated = userService.saveUser(userToSave, userUpdateDto.getRoles());
        return ResponseEntity.ok(userMapper.toResponseDto(updated));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        User user = userService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        boolean isAdmin = user.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ROLE_ADMIN"));

        if (isAdmin) {
            throw new SecurityException("Нельзя удалить пользователя с ролью ADMIN!");
        }

        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}