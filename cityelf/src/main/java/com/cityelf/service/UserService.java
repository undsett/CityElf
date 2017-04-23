package com.cityelf.service;

import com.cityelf.model.User;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private List<User> users = new ArrayList<>();

  public UserService() {
    User user = new User();
    user.setFirstname("Oleg");
    user.setLastname("Mikhailov");
    user.setAdress("Lenina");
    user.setEmail("norco_mount@mail.ru");
    user.setPhone("0936436691");

    users.add(user);

    user = new User();
    user.setFirstname("Sasha");
    user.setLastname("Lusenko");
    user.setAdress("Bunina");
    user.setEmail("sasha_stim@mail.ru");
    user.setPhone("063456321");

    users.add(user);
  }


  public List<User> getAll() {
    return users;
  }


}
