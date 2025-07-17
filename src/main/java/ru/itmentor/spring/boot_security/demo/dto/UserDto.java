package ru.itmentor.spring.boot_security.demo.dto;

import java.util.Set;

public record UserDto(
        Long id,
        String username,
        Set<String> roles
) {}
