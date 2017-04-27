package com.cityelf.controller;

import com.cityelf.model.User;
import com.cityelf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/all")
    public java.util.List<User> getAll() {
        return userService.getAll();
    }
}