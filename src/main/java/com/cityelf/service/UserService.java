package com.cityelf.service;

import static com.cityelf.model.Role.ANONIMUS_ROLE;
import static com.cityelf.model.Role.AUTHORIZED_ROLE;

import com.cityelf.exceptions.AccessDeniedException;
import com.cityelf.exceptions.AddressException;
import com.cityelf.exceptions.AddressNotPresentException;
import com.cityelf.exceptions.Status;
import com.cityelf.exceptions.UserAlreadyExistsException;
import com.cityelf.exceptions.UserException;
import com.cityelf.exceptions.UserNotFoundException;
import com.cityelf.exceptions.UserValidationException;
import com.cityelf.model.Address;
import com.cityelf.model.User;
import com.cityelf.model.UserRole;
import com.cityelf.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private MailSenderService mailSenderService;

  @Autowired
  private SecurityService securityService;
  @Autowired
  private RoleService roleService;
  @Autowired
  private AddressService addressService;

  public UserService() {
  }

  public List<User> getAll() {
    return (List<User>) userRepository.findAll();
  }

  public User getUser(long id) throws UserNotFoundException {
    return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
  }

  public User getUser(String email) throws UserNotFoundException {
    User user = userRepository.findByEmail(email);
    if (user == null) {
      throw new UserNotFoundException();
    }
    return user;
  }

  public long addNewUser(String firebaseId, String addressString)
      throws UserAlreadyExistsException, AddressException {

    if (userRepository.findByFirebaseId(firebaseId).isPresent()) {
      throw new UserAlreadyExistsException();
    }
    User user = createUser(firebaseId, addressString);
    user = userRepository.save(user);
    long id = user.getId();
    Set<UserRole> userRoles = roleService.getUserRoles(id);
    userRoles.add(new UserRole(id, ANONIMUS_ROLE));
    roleService.save(userRoles);
    return id;
  }

  private User createUser(String firebaseId, String addressString) throws AddressException {
    Address address = addressService.getAddress(addressString)
        .orElseThrow(() -> new AddressNotPresentException());
    User user = new User(firebaseId);
    user.setAddresses(Arrays.asList(address));
    return user;
  }

  @Transactional
  public Map<String, Object> registration(String fireBaseID, String email, String password,
      String address)
      throws AddressException {
    Map<String, Object> map = new HashMap<>();
    if (userRepository.findByEmail(email) == null) {
      if (fireBaseID.equals("WEB")) {
        User newUser = userRepository.save(new User(email, password, "WEB"));
        String msg =
            "http://localhost:8088/services/registration/confirm?id=" + newUser.getId()
                + "&email=" + email;
        //mailSenderService.sendMail(email, "Confirm registration CityELF", msg);
        confirmRegistration(newUser.getId(), email);
        map.put("status", Status.USER_REGISTRATION_OK);
        map.put("user", newUser);
        return map;
      }

      if (!fireBaseID.equals("WEB")) {
        User existUser = userRepository.findByFirebaseId(fireBaseID).orElse(null);
        if (existUser != null) {
          return setUserParams(email, password, map, existUser);

        } else {
          existUser = createUser(fireBaseID, address);
          return setUserParams(email, password, map, existUser);
        }
      }
    }
    map.put("status", Status.EMAIL_EXIST);
    return map;
  }


  private Map<String, Object> setUserParams(String email, String password, Map<String, Object> map,
      User existUser) {
    existUser.setEmail(email);
    existUser.setPassword(password);
    existUser.setActivated(true);
    userRepository.save(existUser);
    confirmRegistration(existUser.getId(), email);
    map.put("status", Status.USER_REGISTRATION_OK);
    map.put("user", existUser);
    return map;
  }

  public Status confirmRegistration(long id, String email) {
    User user = userRepository.findByEmail(email);
    if (user.getId() == id) {
      Set<UserRole> userRoles = roleService.getUserRoles(id);
      userRoles.add(new UserRole(id, AUTHORIZED_ROLE));
      roleService.save(userRoles);

      return Status.EMAIL_CONFIRMED;
    }
    return Status.EMAIL_NOT_CONFIRMED;
  }


  public Map<String, Object> login(String email, String password) {
    Map<String, Object> map = new HashMap<>();
    User user = userRepository.findByEmail(email);
    if (user == null || !user.getPassword().equals(password)) {
      map.put("status", Status.LOGIN_OR_PASSWORD_INCORRECT);
    } else {
      map.put("status", Status.LOGIN_PASSWORD_OK);
      map.put("user", user);
    }
    return map;
  }

  public void updateAnonime(User user) throws UserException, AddressException {
    String firebaseId = user.getFirebaseId();
    if (firebaseId == null) {
      throw new UserValidationException("FirebaseId is required");
    }
    User userFromDb = userRepository.findByFirebaseId(firebaseId)
        .orElseThrow(() -> new UserNotFoundException());
    List<Address> addresses = user.getAddresses();
    if (addresses.size() > 1) {
      throw new UserValidationException("Non-authorized user can add one address only");
    }
    userFromDb.setAddresses(addressService.resolveAddresses(addresses));
    userFromDb.setNotification(user.getNotification());
    userRepository.save(userFromDb);
  }

  public User updateUser(User user) throws UserException, AddressException, AccessDeniedException {
    if (securityService.getUserFromSession().getId() != user.getId()) {
      throw new AccessDeniedException();
    }

    User userFromDb = userRepository.findById(user.getId())
        .orElseThrow(() -> new UserNotFoundException());

    List<Address> addresses = addressService.resolveAddresses(user.getAddresses());
    user.setAddresses(addresses);
    Field[] fields = user.getClass().getDeclaredFields();

    AccessibleObject.setAccessible(fields, true);
    for (Field field : fields) {
      Object remoteUserValue = ReflectionUtils.getField(field, user);
      if (remoteUserValue != null) {
        ReflectionUtils.setField(field, userFromDb, remoteUserValue);
      }
    }
    return userRepository.save(userFromDb);
  }

  public void deleteUser(long id) throws UserNotFoundException, AccessDeniedException {
    if (securityService.getUserFromSession().getId() != id) {
      throw new AccessDeniedException();
    }
    User user = userRepository.findOne(id);
    if (user == null) {
      throw new UserNotFoundException();
    }
    user.setActivated(false);
    userRepository.save(user);
  }

  public void unionRecords(String fireBaseID) throws UserNotFoundException {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String name = auth.getName(); //get logged in username
    User userWeb = userRepository.findByEmail(name);
    if (!fireBaseID.equals("WEB") && userWeb.getFirebaseId().equals("WEB")) {
      User userAndroid = userRepository.findByFirebaseId(fireBaseID)
          .orElseThrow(() -> new UserNotFoundException());
      userAndroid.setEmail(userWeb.getEmail());
      userAndroid.setPassword(userWeb.getPassword());
      userRepository.save(userAndroid);
      userWeb.setEmail(null);
      userWeb.setPassword(null);
      userRepository.save(userWeb);
    }
  }
}