package ru.itmentor.spring.boot_security.demo.service;

import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.repository.RoleRepository;

import java.util.List;

public interface RoleService {

    List<Role> findAll();

    List<Role> findByIds(List<Long> ids);
}
