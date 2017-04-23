package com.cityelf.service;

import com.cityelf.model.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  HashMap<String, User> users = new HashMap();

  public UserService() {
    User user = new User();
    user.setFirstname("Oleg");
    user.setLastname("Mikhailov");
    user.setAdress("Lenina");
    user.setEmail("norco_mount@mail.ru");
    user.setPhone("0936436691");

    users.put(user.getEmail(), user);

    user = new User();
    user.setFirstname("Sasha");
    user.setLastname("Lusenko");
    user.setAdress("Bunina");
    user.setEmail("sasha_stim@mail.ru");
    user.setPhone("063456321");

    users.put(user.getEmail(), user);
  }


  public User getUser(String email) {
    if (users.containsKey(email)) {
      return users.get(email);
    } else {
      return null;
    }
  }

  public HashMap<String, User> getAll() {
    return users;
  }


}
