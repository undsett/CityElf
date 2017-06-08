package com.cityelf.service;

import com.cityelf.exceptions.UserNotFoundException;
import com.cityelf.model.User;
import com.cityelf.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public UserService() {
  }

  public List<User> getAll() {
    return (List<User>) userRepository.findAll();
  }

  public User getUser(long id){
    return userRepository.findOne(id);
  }

  public void addNewUser(User user) {
    userRepository.save(user);
  }

  public void updateUser(User user) {
    userRepository.save(user);
  }

  public void deleteUser(long id) {
   userRepository.delete(id);
  }
}
