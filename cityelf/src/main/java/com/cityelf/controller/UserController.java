package com.cityelf.controller;

import com.cityelf.model.User;
import com.cityelf.service.UserService;
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

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
    public User get(@PathVariable("userId") long id) {
        return userService.get(id);
    }

    @RequestMapping(value = "/users", method = RequestMethod.PUT)
    public void put(@RequestBody User user) {
        userService.put(user);
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public void post(@RequestBody User user) {
        userService.post(user);
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.DELETE)
    public void delete(@RequestBody long id) {
        userService.delete(id);
    }

}