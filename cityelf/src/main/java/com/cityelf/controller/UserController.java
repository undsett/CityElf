package com.cityelf.controller;

import com.cityelf.model.User;
import com.cityelf.service.UserService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/all")
    public List<User> getAll() {
        return userService.getAll();
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public User getUser(@PathVariable("userId") long id) {
        return userService.getUser(id);
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.PUT)
    public void updateUser(@RequestBody User user) {
        userService.updateUser(user);
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public void addNewUser(@RequestBody User user) {
        userService.addNewUser(user);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public void deleteUser(@RequestBody long id) {
        userService.deleteUser(id);
    }

}