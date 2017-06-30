package com.cityelf.controller;

import com.cityelf.exceptions.UserAlreadyExistsException;
import com.cityelf.exceptions.UserException;
import com.cityelf.exceptions.UserNotFoundException;
import com.cityelf.exceptions.UserValidationException;
import com.cityelf.model.User;
import com.cityelf.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

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
  public void updateUser(@RequestBody @Valid User user, BindingResult bindingResult)
      throws UserException {
    if (bindingResult.hasErrors()) {
      String errorMessage = bindingResult.getFieldErrors()
          .stream()
          .map(error -> error.getDefaultMessage())
          .collect(Collectors.joining(", "));
      throw new UserValidationException(errorMessage);
    }
    userService.updateUser(user);
  }

<<<<<<< HEAD
  @RequestMapping(value = "/adduser", method = RequestMethod.POST)
public void addNewUser(@RequestParam(name = "login") String email,
@RequestParam(name = "password") String password,
@RequestParam(name = "address") String adress,
@RequestParam(name = "firebaseid") String fireBaseID)
throws UserAlreadyExistsException {
userService.addNewUser(email, password, adress, fireBaseID);
    }

=======
  @RequestMapping(value = "/addUser", method = RequestMethod.POST)
  public User addNewUser(@RequestBody @Valid User user, BindingResult bindingResult)
      throws UserException {
    if (bindingResult.hasErrors()) {
      String errorMessage = bindingResult.getFieldErrors()
          .stream()
          .map(error -> error.getDefaultMessage())
          .collect(Collectors.joining(", "));
      throw new UserValidationException(errorMessage);
    }
    return userService.addNewUser(user);
  }
>>>>>>> 1221839589b08e2cd608753fe78aa0f5836a8fd5

  @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
  public void deleteUser(@PathVariable("userId") long id) throws UserNotFoundException {
    userService.deleteUser(id);
  }
}