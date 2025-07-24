package ru.itmentor.spring.boot_security.demo.service;

import org.springframework.http.ResponseEntity;
import ru.itmentor.spring.boot_security.demo.dto.UserResponseDto;
import ru.itmentor.spring.boot_security.demo.dto.UserUpdateDto;
import ru.itmentor.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {
    List<UserResponseDto> findAll();
    Optional<User> findById(Long id);
    ResponseEntity<UserResponseDto> getUserResponseById(Long id);
    ResponseEntity<UserResponseDto> saveUser(UserUpdateDto dto);
    ResponseEntity<UserResponseDto> updateUser(Long id, UserUpdateDto userUpdateDto);
    ResponseEntity<Void> deleteById(Long id);
}
