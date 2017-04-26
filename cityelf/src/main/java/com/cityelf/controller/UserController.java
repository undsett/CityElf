package main.java.com.cityelf.controller;

import main.java.com.cityelf.model.User;
import main.java.com.cityelf.service.UserService;
import main.java.com.cityelf.controller.RestController;
import main.java.org.springframework.beans.factory.annotation.Autowired;
import main.java.org.springframework.web.bind.annotation.*;

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
    public User get(@PathVariable("userId") long id) {
        return userService.get(id);
    }

    @RequestMapping(value = "/put", method = RequestMethod.PUT)
    public void put(@RequestBody User user) {
        userService.put(user);
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public void post(@RequestBody User user) {
        userService.post(user);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public void delete(@RequestBody long id) {
        userService.delete(id);
    }

}