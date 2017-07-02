package com.cityelf.service;

import com.cityelf.exceptions.Statuses;
import com.cityelf.exceptions.UserAlreadyExistsException;
import com.cityelf.exceptions.UserException;
import com.cityelf.exceptions.UserNoFirebaseIdException;
import com.cityelf.exceptions.UserNotAuthorizedException;
import com.cityelf.exceptions.UserNotFoundException;
import com.cityelf.model.Address;
import com.cityelf.model.User;
import com.cityelf.model.UserAddresses;
import com.cityelf.model.UserRole;
import com.cityelf.repository.AddressesRepository;
import com.cityelf.repository.UserAddressesRepository;
import com.cityelf.repository.UserRepository;
import com.cityelf.repository.UserRoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserAddressesRepository userAddressesRepository;

  @Autowired
  private AddressesRepository addressesRepository;

  @Autowired
  private UserRoleRepository userRoleRepository;

  @Autowired
  private MailSenderService mailSenderService;

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


  public Statuses addNewUser(String firebaseId, String address)
      throws UserAlreadyExistsException {
    User newUser;

    if (userRepository.findByFirebaseId(firebaseId) == null) {
      userRepository.save(new User(firebaseId));
      newUser = userRepository.findByFirebaseId(firebaseId);
      Address address1 = addressesRepository.findByAddress(address);
      long idAddress = address1.getId();
      userAddressesRepository
          .save(new UserAddresses(newUser.getId(), idAddress));
      userRoleRepository.save(new UserRole(newUser.getId(), 1));
      return Statuses.USER_ADD_IN_DB_OK;
    } else {
      return Statuses.USER_EXIIST;
    }
  }

  public Statuses registration(String fireBaseID, String email, String password) {
    User newUser = userRepository.findByFirebaseId(fireBaseID);

    if (newUser != null && !fireBaseID.equals("WEB")) {

      newUser.setEmail(email);
      newUser.setPassword(password);
      userRepository.save(newUser);
      String msg = "http://localhost:8088/services/users/confirmregistration?id=" + newUser.getId()
          + "&email=" + email;
      mailSenderService.sendMail(email, "Confirm registration CityELF", msg);

      return Statuses.USER_REGISTRATION_OK;
    } else {
      if (fireBaseID.equals("WEB") && userRepository.findByEmail(email) == null) {
        userRepository.save(new User(email, password, "WEB"));
        newUser = userRepository.findByEmail(email);
        String msg =
            "http://localhost:8088/services/users/confirmregistration?id=" + newUser.getId()
                + "&email=" + email;
        mailSenderService.sendMail(email, "Confirm registration CityELF", msg);
        return Statuses.USER_REGISTRATION_OK;
      }
    }

    return Statuses.EMAIL_EXIST;
  }

  public Statuses confirmRegistration(String id, String email) {
    long idUser = Long.parseLong(id);
    User user = userRepository.findByEmail(email);
    if (user.getId() == idUser) {
      user.setActivated(true);
      userRepository.save(user);
      UserRole userRole = userRoleRepository.findByUserId(user.getId());
      if (userRole == null) {
        userRoleRepository.save(new UserRole(user.getId(), 3));
      } else {
        userRole.setRoleId(3);
        userRoleRepository.save(userRole);
      }

      return Statuses.EMAIL_CONFIRMED;
    }
    return Statuses.EMAIL_NOT_CONFIRMED;
  }

  public Statuses login(String email, String password) {
    if (userRepository.findByEmail(email) == null) {
      return Statuses.LOGIN_INCORRECT;
    } else if (userRepository.findByEmail(email).getPassword().equals(password)) {
      return Statuses.LOGIN_PASSWORD_OK;
    }
    return Statuses.PASSWORD_INCORRECT;

  }

  public void updateUser(User user) throws UserException {
    User userFromDb = userRepository.findOne(user.getId());
    if (userFromDb != null) {
      Field[] fields = user.getClass().getDeclaredFields();
      AccessibleObject.setAccessible(fields, true);
      for (Field field : fields) {
        if (ReflectionUtils.getField(field, user) == null) {
          Object valueFromDb = ReflectionUtils.getField(field, userFromDb);
          ReflectionUtils.setField(field, user, valueFromDb);
        }
      }
      if (user.getAddresses().size() == 0) {
        user.setAddresses(userFromDb.getAddresses());
      }
      if ("not_authorized".equals(user.getAuthorized()) && user.getAddresses().size() > 1) {
        throw new UserNotAuthorizedException();
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