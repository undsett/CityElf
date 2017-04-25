package com.cityelf.repository;

import com.cityelf.model.User;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
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

        users.add(user);

        user = new User();
        user.setId(2);
        user.setFirstname("Sasha");
        user.setLastname("Lusenko");
        user.setAddress("Bunina");
        user.setEmail("sasha_asdasfas@mail.ru");
        user.setPhone("063456321");

        users.add(user);

        user = new User();
        user.setId(3);
        user.setFirstname("Vasya");
        user.setLastname("Pupkin");
        user.setAddress("Rishel'evskaya");
        user.setEmail("vasya_pupkin@gmail.com");
        user.setPhone("0735556644");

        users.add(user);

        user = new User();
        user.setId(4);
        user.setFirstname("Vlada");
        user.setLastname("Testovaya");
        user.setAddress("Levitana");
        user.setEmail("vlada_test@rambler.ru");
        user.setPhone("0487865432");

        users.add(user);

        user = new User();
        user.setId(5);
        user.setFirstname("Roma");
        user.setLastname("Odesskij");
        user.setAddress("Vodoprovodnaya");
        user.setEmail("romaodessa@ukr.net");
        user.setPhone("0946677321");

        users.add(user);
    }

    public List<User> getUsers() {
        return users;
    }

    public User get(long id) {
        return users.get((int) id);
    }

    public void post(User user) {
        users.add(user);
    }

    public void put(User user) {
        if (users.contains(user)) {
            users.set((int) user.getId(), user);
        } else {
            post(user);
        }
    }

    public void delete(long id) {
        users.remove(id);
    }
}