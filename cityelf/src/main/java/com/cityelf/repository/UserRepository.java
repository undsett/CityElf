package com.cityelf.repository;

import com.cityelf.model.User;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserRepository {

  private List<User> users = new ArrayList();

  public UserRepository() {
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
    user.setEmail("sasha_asdasfas@mail.ru");
    user.setPhone("063456321");

    users.add(user);
  }

  public List<User> getUsers() {
    return users;
  }
}