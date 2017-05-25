package com.cityelf.repository;

import com.cityelf.model.Notification;
import com.cityelf.model.User;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

  private List<User> users = new ArrayList();

  public UserRepository() {
    User user = new User();
    user.setId(1);
    user.setFirstname("Oleg");
    user.setLastname("Mikhailov");
    user.setAddress("Lenina");
    user.setEmail("norco_mount@mail.ru");
    user.setPhone("0936436691");
    user.setNotification(new Notification());
    user.getRecentAddresses().add("Pushkinskaya, 16");
    user.getRecentAddresses().add("Zhukovskogo, 4");

    users.add(user);

    user = new User();
    user.setId(2);
    user.setFirstname("Sasha");
    user.setLastname("Lusenko");
    user.setAddress("Bunina");
    user.setEmail("sasha_asdasfas@mail.ru");
    user.setPhone("063456321");
    user.setNotification(new Notification());
    user.getRecentAddresses().add("Primorskii, 10");

    users.add(user);

    user = new User();
    user.setId(3);
    user.setFirstname("Vasya");
    user.setLastname("Pupkin");
    user.setAddress("Rishel'evskaya");
    user.setEmail("vasya_pupkin@gmail.com");
    user.setPhone("0735556644");
    user.setNotification(new Notification());

    users.add(user);

    user = new User();
    user.setId(4);
    user.setFirstname("Vlada");
    user.setLastname("Testovaya");
    user.setAddress("Levitana");
    user.setEmail("vlada_test@rambler.ru");
    user.setPhone("0487865432");
    user.setNotification(new Notification());

    users.add(user);

    user = new User();
    user.setId(5);
    user.setFirstname("Roma");
    user.setLastname("Odesskij");
    user.setAddress("Vodoprovodnaya");
    user.setEmail("romaodessa@ukr.net");
    user.setPhone("0946677321");
    user.setNotification(new Notification());

    users.add(user);
  }

  public List<User> getUsers() {
    return users;
  }

  public Optional<User> getUser(long id) {
    return users.stream().filter(user -> user.getId() == id).findFirst();
  }

  public void addNewUser(User user) {
    users.add(user);
  }

  public boolean updateUser(User user) {
    long id = user.getId();
    for (User userDb : users) {
      if (userDb.getId() == id) {
        users.set(users.indexOf(userDb), user);
        return true;
      }
    }
    return false;
  }

  public boolean deleteUser(long id) {
    return users.removeIf(user -> user.getId() == id);
  }
}