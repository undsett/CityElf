package com.cityelf.controller;


import com.cityelf.service.UserService;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cityelf.model.User;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  UserService us;

  @RequestMapping("/all")
  public HashMap<String, User> getAll() {
    return us.getAll();
  }

  @RequestMapping("{email}")
  public User getUser(@PathVariable("email") String email) {
    return us.getUser(email);
  }

}
