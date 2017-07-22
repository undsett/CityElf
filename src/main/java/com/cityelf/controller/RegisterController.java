package com.cityelf.controller;

import com.cityelf.exceptions.Status;
import com.cityelf.exceptions.UserAlreadyExistsException;
import com.cityelf.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registration")
public class RegisterController {

  @Autowired
  private UserService userService;

  @RequestMapping(value = "/adduser", method = RequestMethod.POST)
  public Status addNewUser(@RequestParam(name = "firebaseid") String fireBaseID,
      @RequestParam(name = "address") String address)
      throws UserAlreadyExistsException {
    return userService.addNewUser(fireBaseID, address);
  }


  @RequestMapping(value = "/register", method = RequestMethod.POST)
  public Status registration(@RequestParam(name = "firebaseid") String fireBaseID,
      @RequestParam(name = "email") String email, @RequestParam(name = "password") String password)
      throws UserAlreadyExistsException {
    return userService.registration(fireBaseID, email, password);
  }

  @RequestMapping(value = "/confirm", method = RequestMethod.GET)
  public Status confirmregistration(@RequestParam(name = "id") String id,
      @RequestParam(name = "email") String email) {
    return userService.confirmRegistration(id, email);
  }

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public Status login(@RequestParam(name = "email") String email,
      @RequestParam(name = "password") String password) {
    return userService.login(email, password);
  }
}
