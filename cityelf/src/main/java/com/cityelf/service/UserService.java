package com.cityelf.service;

import com.cityelf.model.User;
import com.cityelf.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private
  UserRepository userRepository;

  public List<User> getAll() {
    return userRepository.getUsers();
  }


}
