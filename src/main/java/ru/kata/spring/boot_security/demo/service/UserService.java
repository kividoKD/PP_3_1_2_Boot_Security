package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    User addUser(User user);

    List<User> getListUsers();

    User getUser(Long id);

    User updateUser(Long id, User user);

    void deleteUser(Long id);
}
