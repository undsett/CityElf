package com.cityelf.controller;

import com.cityelf.exceptions.UserNotFoundException;
import com.cityelf.model.User;
import com.cityelf.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserService userService;

  @RequestMapping("/all")
  public java.util.List<User> getAll() {
    return userService.getAll();
  }

  @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
  public User getUser(@PathVariable("userId") long id) throws UserNotFoundException {
    return userService.getUser(id);
  }

  @RequestMapping(value = "/updateUser", method = RequestMethod.PUT)
  public void updateUser(@RequestBody User user) throws UserNotFoundException {
    userService.updateUser(user);
  }

  @RequestMapping(value = "/addUser", method = RequestMethod.POST)
  public void addNewUser(@RequestBody User user) {
    userService.addNewUser(user);
  }

  @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
  public void deleteUser(@PathVariable("userId") long id) throws UserNotFoundException {
    userService.deleteUser(id);
  }

  @RequestMapping(value = "/recentAddresses/{userId}", method = RequestMethod.GET)
  public List<String> getRecentAddresses(@PathVariable("userId") long id)
      throws UserNotFoundException {
    return userService.getUser(id).getRecentAddresses();
  }
}