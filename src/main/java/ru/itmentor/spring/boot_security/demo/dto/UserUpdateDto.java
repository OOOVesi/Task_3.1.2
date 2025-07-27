package ru.itmentor.spring.boot_security.demo.dto;

import java.util.Set;

public class UserUpdateDto {
    private Long id;
    private String username;
    private String password;
    private Set<String> roles;

    // Пустой конструктор
    public UserUpdateDto() {
    }

    // Конструктор со всеми полями
    public UserUpdateDto(Long id, String username, String password, Set<String> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    // Геттеры и сеттеры

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}