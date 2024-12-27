package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

public interface RoleService {
    void addRole(Role role);

    List<Role> getListRoles();

    Role getRole(Long id);

    void updateRole(Long id, Role role);

    void deleteRole(Long id);
}
