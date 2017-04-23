package com.cityelf.service;

import com.cityelf.model.User;
import com.cityelf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService{
  @Autowired
  private UserRepository userRepository;

  public UserService() {}

  public java.util.List<User> getAll()
  {
    return userRepository.getUsers();
  }
}
