package com.cityelf.service;

import com.cityelf.exceptions.UserAlreadyExistsException;
import com.cityelf.exceptions.UserNotFoundException;
import com.cityelf.model.User;
import com.cityelf.model.UserAddresses;
import com.cityelf.repository.AddressesRepository;
import com.cityelf.repository.AddressesRepository;
import com.cityelf.repository.UserAddressesRepository;
import com.cityelf.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserAddressesRepository userAddressesRepository;

  @Autowired
  private AddressesRepository addressesRepository;

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


  public void addNewUser(String email, String password, String address, String firebaseId)
      throws UserAlreadyExistsException {
    User newUser;

    if (userRepository.findByEmail(email) == null
        && userRepository.findByFirebaseId(firebaseId) == null) {
      newUser = new User(email, password, firebaseId);
      userRepository.save(newUser);
      long idAddress = addressesRepository.findByAddress(address).getId();
      UserAddresses userAddresses = new UserAddresses(
          userRepository.findByFirebaseId(firebaseId).getId(), idAddress);
      userAddressesRepository.save(userAddresses);

    } else {
      if (firebaseId.equals(userRepository.findByFirebaseId(firebaseId).getFirebaseId())
          && userRepository.findByEmail(email) == null) {
        newUser = userRepository.findByFirebaseId(firebaseId);
        newUser.setEmail(email);
        newUser.setPassword(password);
        userRepository.save(newUser);
      } else {
        throw new UserAlreadyExistsException();
      }
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