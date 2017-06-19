package com.cityelf.controller;

import com.cityelf.exceptions.UserAlreadyExistsException;
import com.cityelf.exceptions.UserNotFoundException;
import com.cityelf.model.User;
import com.cityelf.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
  public User getUser(@PathVariable("userId") long id) throws UserNotFoundException {
    return userService.getUser(id);
  }

  @RequestMapping(value = "/updateUser", method = RequestMethod.PUT)
  public void updateUser(@RequestBody User user) throws UserNotFoundException {
    userService.updateUser(user);
  }

  @RequestMapping(value = "/adduser", method = RequestMethod.POST)
  public void addNewUser(@RequestParam(name = "login") String email,
      @RequestParam(name = "password") String password,
      @RequestParam(name = "address") String adress,
      @RequestParam(name = "firebaseid") String fireBaseID)
      throws UserAlreadyExistsException {
    userService.addNewUser(email,password,adress, fireBaseID);
  }



  @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
  public void deleteUser(@PathVariable("userId") long id) throws UserNotFoundException {
    userService.deleteUser(id);
  }
}