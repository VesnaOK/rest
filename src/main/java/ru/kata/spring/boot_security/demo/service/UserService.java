package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    public List<User> getAllUsers();

    public User getUserById(Long id);

    public void saveUser(User user);

    public User getUserAndRoles(User user, String[] roles);


    public void deleteUser(Long id);

    public User getNotNullRole(User user);
}
