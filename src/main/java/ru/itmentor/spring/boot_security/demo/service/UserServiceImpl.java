package ru.itmentor.spring.boot_security.demo.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itmentor.spring.boot_security.demo.dto.UserResponseDto;
import ru.itmentor.spring.boot_security.demo.dto.UserUpdateDto;
import ru.itmentor.spring.boot_security.demo.mapper.UserMapper;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.repository.RoleRepository;
import ru.itmentor.spring.boot_security.demo.repository.UserRepository;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List <UserResponseDto> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<UserResponseDto> getUserResponseById(Long id) { //разделение с findById, всё для @GetMapping("/users/{id}")
        return findById(id)
                .map(userMapper::toResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @Transactional
    public ResponseEntity<UserResponseDto> saveUser(UserUpdateDto dto) {
        User user = userMapper.toUser(dto);

        Set<Role> roles = roleRepository.findAll().stream()
                .filter(role -> dto.getRoles().contains(role.getName()))
                .collect(Collectors.toSet());
        user.setRoles(roles);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User saved = userRepository.save(user);
        UserResponseDto responseDto = userMapper.toResponseDto(saved);
        return ResponseEntity.ok(responseDto);
    }

    @Override
    @Transactional
    public ResponseEntity<UserResponseDto> updateUser(Long id, UserUpdateDto userUpdateDto) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        userUpdateDto.setId(id);
        User userToSave = userMapper.toUser(userUpdateDto);

        Set<Role> roles = roleRepository.findAll().stream()
                .filter(role -> userUpdateDto.getRoles().contains(role.getName()))
                .collect(Collectors.toSet());
        userToSave.setRoles(roles);

        if (userToSave.getPassword() != null && !userToSave.getPassword().isBlank()) {
            userToSave.setPassword(passwordEncoder.encode(userToSave.getPassword()));
        } else {
            userToSave.setPassword(existing.getPassword());
        }

        User updated = userRepository.save(userToSave);
        UserResponseDto responseDto = userMapper.toResponseDto(updated);

        return ResponseEntity.ok(responseDto);
    }

    @Override
    @Transactional
    public ResponseEntity<Void> deleteById(Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

