package com.cityelf.service;

import com.cityelf.exceptions.Status;
import com.cityelf.exceptions.UserAlreadyExistsException;
import com.cityelf.exceptions.UserException;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


  public Status addNewUser(String firebaseId, String address)
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
      return Status.USER_ADD_IN_DB_OK;
    } else {
      return Status.USER_EXIIST;
    }
  }

  public Status registration(String fireBaseID, String email, String password) {
    User newUser = new User();
    if (fireBaseID.equals("WEB") && userRepository.findByEmail(email) == null) {
      userRepository.save(new User(email, password, "WEB"));
      newUser = userRepository.findByEmail(email);
      String msg =
          "http://localhost:8088/services/registration/confirm?id=" + newUser.getId()
              + "&email=" + email;
      mailSenderService.sendMail(email, "Confirm registration CityELF", msg);
      return Status.USER_REGISTRATION_OK;
    }

    if (!fireBaseID.equals("WEB")) {
      User existUser = userRepository.findByFirebaseId(fireBaseID);
      if (existUser != null && userRepository.findByEmail(email) == null) {
        existUser.setEmail(email);
        existUser.setPassword(password);
        userRepository.save(existUser);
        String msg = "http://localhost:8088/services/registration/confirm?id=" + existUser.getId()
            + "&email=" + email;
        mailSenderService.sendMail(email, "Confirm registration CityELF", msg);
        return Status.USER_REGISTRATION_OK;
      }
    }

    return Status.EMAIL_EXIST;
  }

  public Status confirmRegistration(String id, String email) {
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

      return Status.EMAIL_CONFIRMED;
    }
    return Status.EMAIL_NOT_CONFIRMED;
  }

  public Map<String, Object> login(String email, String password) {
    Map<String, Object> map = new HashMap<>();
    User user = userRepository.findByEmail(email);
    if (user == null || !user.getPassword().equals(password)) {
      map.put("status", Status.LOGIN_INCORRECT);
    } else {
      map.put("status", Status.LOGIN_PASSWORD_OK);
      map.put("user", user);
    }

    return map;

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