package ru.itmentor.spring.boot_security.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import ru.itmentor.spring.boot_security.demo.dto.UserResponseDto;
import ru.itmentor.spring.boot_security.demo.dto.UserUpdateDto;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(source = "roles", target = "roles", qualifiedByName = "rolesToStrings")
    UserResponseDto toResponseDto(User user);

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "enabled", constant = "true")
    User toUser(UserUpdateDto dto);

    @Mapping(source = "roles", target = "roles", qualifiedByName = "rolesToStrings")
    UserUpdateDto toUserUpdateDto(User user);

    @Named("rolesToStrings")
    static Set<String> rolesToStrings(Set<Role> roles) {
        if (roles == null) {
            return Collections.emptySet();
        }
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }
}