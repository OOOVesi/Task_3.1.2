package ru.itmentor.spring.boot_security.demo.service;

import ru.itmentor.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {
    List<User> findAll();
    Optional<User> findById(Long id);
    User saveUser(User user, Set<String> roleNames);
    void deleteById(Long id);
}
