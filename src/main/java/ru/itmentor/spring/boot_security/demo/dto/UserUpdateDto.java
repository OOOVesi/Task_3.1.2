package ru.itmentor.spring.boot_security.demo.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserUpdateDto {
    private Long id;
    private String username;
    private String password;
    private Set<String> roles;

    public Set<String> getRoles() {
        return roles;
    }

}