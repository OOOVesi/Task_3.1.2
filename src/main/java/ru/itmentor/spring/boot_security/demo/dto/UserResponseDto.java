package ru.itmentor.spring.boot_security.demo.dto;

import java.util.Set;

public class UserResponseDto {
    private Long id;
    private String username;
    private Set<String> roles; // "ROLE_USER", "ROLE_ADMIN"

    public UserResponseDto() {
    }

    public UserResponseDto(Long id, String username, Set<String> roles) {
        this.id = id;
        this.username = username;
        this.roles = roles;
    }

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

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}