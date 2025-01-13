package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;

    public AdminController(UserServiceImpl userService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @ModelAttribute
    public void addCommonAttributes(ModelMap model, Principal principal) {
        model.addAttribute("activePage", "admin");
        model.addAttribute("rolesFromDb", roleService.getListRoles());
        model.addAttribute("userFromPrincipal", userService.findByUsername(principal.getName()));
    }

    @GetMapping
    public String getUsersList(@RequestParam(value = "count", defaultValue = "5") String count,
                               ModelMap model) {
        model.addAttribute("count", count);
        model.addAttribute("users", userService.getListUsers());
        model.addAttribute("newUser", new User());
        return "admin/admin";
    }

//    @GetMapping("/getUser")
//    public User getUser(Long id) {
//        return userService.getUser(id);
//    }

    @GetMapping("/new")
    public String createUser(@ModelAttribute("user") User user) {
        return "admin/new";
    }

    @PostMapping()
    public String addUser(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/admin";
    }

//    @PatchMapping("/{id}")
//    public String updateUserPatchMapping(@ModelAttribute User user, @PathVariable("id") Long id) {
//        userService.updateUser(id, user);
//        return "redirect:/admin";
//    }

//    @DeleteMapping("/{id}")
//    public String deleteUserPatchMapping(@PathVariable("id") Long id) {
//        userService.deleteUser(id);
//        return "redirect:/admin";
//    }
}
