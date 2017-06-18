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
    if (user.getEmail() == null && userRepository.findByFirebaseId(user.getFirebaseId()) == null) {
      user.setAuthorized("not_authorized");
      userRepository.save(user);
    } else if (user.getFirebaseId() == null
        && userRepository.findByEmail(user.getEmail()) == null) {
      user.setAuthorized("authorized");
      userRepository.save(user);
    } else if (userRepository.findByFirebaseId(user.getFirebaseId()) == null
        && userRepository.findByEmail(user.getEmail()) == null) {
      user.setAuthorized("authorized");
      userRepository.save(user);
    } else {
      throw new UserAlreadyExistsException();
    }
  }

  public void updateUser(User user) throws UserNotFoundException {
    if (userRepository.exists(user.getId())) {
      User userFromDB = userRepository.findOne(user.getId());
      if (user.getPassword() == null) {
        user.setPassword(userFromDB.getPassword());
      }
      if (user.getFirebaseId() == null) {
        user.setFirebaseId(userFromDB.getFirebaseId());
      }
      if (user.getEmail() == null) {
        user.setEmail(userFromDB.getEmail());
      }
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