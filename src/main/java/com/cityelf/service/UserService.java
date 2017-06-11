package com.cityelf.service;

import com.cityelf.exceptions.UserAlreadyExistsException;
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

  public User getUser(long id) throws UserNotFoundException {
    User user = userRepository.findOne(id);
    if (user == null) {
      throw new UserNotFoundException();
    }
    return user;
  }

  public void addNewUser(User user) throws UserAlreadyExistsException {
    if (userRepository.findByEmail(user.getEmail()) == null) {
      userRepository.save(user);
    } else {
      throw new UserAlreadyExistsException();
    }
  }

  public void updateUser(User user) throws UserNotFoundException {
    if (userRepository.exists(user.getId())) {
      userRepository.save(user);
    } else {
      throw new UserNotFoundException();
    }
  }

  public void deleteUser(long id) throws UserNotFoundException {
    if (userRepository.findOne(id) == null) {
      throw new UserNotFoundException();
    }
    userRepository.delete(id);
  }
}
