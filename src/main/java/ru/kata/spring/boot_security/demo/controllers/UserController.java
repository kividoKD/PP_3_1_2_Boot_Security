package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;


@Controller
@RequestMapping("/user")
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showUserPage() {
        return "user/user";
    }

    @ModelAttribute("userFromPrincipal")
    public User getUserFromPrincipal(Principal principal) {
        return userService.findByUsername(principal.getName());
    }

    @ModelAttribute("activePage")
    public String returnModelAttributeActivePage() {
        return "user";
    }
}
