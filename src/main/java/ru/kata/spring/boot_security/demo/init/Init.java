package ru.kata.spring.boot_security.demo.init;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class Init {
    private final RoleServiceImpl roleService;
    private final UserServiceImpl userService;

    public Init(RoleServiceImpl roleService, UserServiceImpl userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @PostConstruct
    private void postConstruct() {
        Role roleAdmin = new Role(1L, "ROLE_ADMIN");
        Role roleUser = new Role(2L, "ROLE_USER");

        User admin = new User("admin", "admin@admin.admin", new HashSet<Role>(Arrays.asList(roleAdmin, roleUser)), "admin");
        User user = new User("user", "user@user.user", Collections.singleton(roleUser), "user");

        roleService.addRole(roleAdmin);
        roleService.addRole(roleUser);
        userService.addUser(admin);
        userService.addUser(user);
    }
}
