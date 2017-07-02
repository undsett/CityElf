package com.cityelf.controller;

import com.cityelf.exceptions.Statuses;
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
public class RegisterController {

  @Autowired
  private UserService userService;

  @RequestMapping(value = "/adduser", method = RequestMethod.POST)
  public Statuses addNewUser(@RequestParam(name = "firebaseid") String fireBaseID,
      @RequestParam(name = "address") String address)
      throws UserAlreadyExistsException {
    return userService.addNewUser(fireBaseID, address);
  }


  @RequestMapping(value = "/registration", method = RequestMethod.POST)
  public Statuses registration(@RequestParam(name = "firebaseid") String fireBaseID,
      @RequestParam(name = "email") String email, @RequestParam(name = "password") String password)
      throws UserAlreadyExistsException {
    return userService.registration(fireBaseID, email, password);
  }

  @RequestMapping(value = "/confirmregistration", method = RequestMethod.GET)
  public Statuses confirmregistration(@RequestParam(name = "id") String id,
      @RequestParam(name = "email") String email){
    return userService.confirmRegistration(id,email);
  }

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public Statuses login(@RequestParam(name = "email") String email,
      @RequestParam(name = "password") String password){
    return userService.login(email,password);
  }


}
