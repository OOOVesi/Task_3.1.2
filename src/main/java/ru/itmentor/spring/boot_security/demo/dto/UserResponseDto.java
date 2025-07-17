package ru.itmentor.spring.boot_security.demo.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserResponseDto {
    private Long id;
    private String username;
    private Set<String> roles; // "ROLE_USER", "ROLE_ADMIN"

    public Set<String> getRoles() {
        return roles;
    }
}