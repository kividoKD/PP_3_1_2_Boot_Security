package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping
    public String getUsersList(@RequestParam(value = "count", defaultValue = "5") String count,
                               ModelMap model) {
        model.addAttribute("count", count);
        model.addAttribute("users", userService.getListUsers());
        model.addAttribute("rolesFromDb", roleService.getListRoles());
        model.addAttribute("activePage", "admin");
        model.addAttribute("newUser", new User());
        return "admin/admin";
    }

//    @GetMapping
//    public String getUsersList(@RequestParam(value = "count", defaultValue = "5") String count,
//                               ModelMap model,
//                               Long id) {
//        model.addAttribute("count", count);
//        model.addAttribute("users", userService.getListUsers());
//        model.addAttribute("rolesFromDb", roleService.getListRoles());
//        model.addAttribute("activePage", "admin");
//        model.addAttribute("newUser", getUser(id));
//        return "admin/admin";
//    }

    @GetMapping("/getUser")
    public User getUser(Long id) {
        return userService.getUser(id);
    }

    @GetMapping("/{id}/edit")
    public String editUser(ModelMap model, @PathVariable("id") Long id) {
        model.addAttribute("rolesFromDb", roleService.getListRoles());
        model.addAttribute("user", userService.getUser(id));
        return "admin/edit";
    }

    @PostMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") Long id) {
        userService.updateUser(id, user);
        return "redirect:/admin";
    }

    @PatchMapping("/{id}")
    public String updateUserPatchMapping(@ModelAttribute("user") User user, @PathVariable("id") Long id) {
        userService.updateUser(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deleteUserPatchMapping(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/new")
    public String createUser(@ModelAttribute("newUser") User user, Model model) {
        model.addAttribute("rolesFromDb", roleService.getListRoles());
        model.addAttribute("activePage", "admin");
        return "admin/new";
    }

    @PostMapping()
    public String addUser(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/admin";
    }

    @ModelAttribute("user")
    public User returnModelAttributeUserFromPrincipal(Principal principal) {
        return userService.findByUsername(principal.getName());
    }

    @ModelAttribute("activePage")
    public String returnModelAttributeActivePage() {
        return "admin";
    }
}
