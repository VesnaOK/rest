package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.Collections;
import java.util.List;

@Service

public class UserServiceImp implements UserService {
    private final UserRepository repository;
    private final RoleService roleService;
    private final PasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


    public UserServiceImp(UserRepository repository, RoleService roleService) {
        this.repository = repository;
        this.roleService = roleService;

    }


    public List<User> getAllUsers() {
        return repository.findAll();
    }


    public User getUserById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    public void saveUser(User user) {


        //user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        repository.save(user);
    }
    @Transactional
    public User getUserAndRoles(User user, String[] roles) {
        if (roles == null) {
            user.setRoles(roleService.getRoleByName(new String[]{"ROLE_USER"}));
        } else {
            user.setRoles(roleService.getRoleByName(roles));
        }
        return user;
    }
    @Transactional
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public User getNotNullRole(User user) {
        if (user.getRoles() == null) {
            user.setRoles(Collections.singleton(new Role(2L)));
        }
        return user;
    }

}
