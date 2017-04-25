package com.cityelf.service;

import com.cityelf.model.User;
import com.cityelf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserService() {
    }

    public List<User> getAll() {
        return userRepository.getUsers();
    }

    public User get(long id) {
        return userRepository.get(id);
    }

    public void post(User user) {
        userRepository.post(user);
    }

    public void put(User user) {
        userRepository.put(user);
    }

    public void delete(long id) {
        userRepository.delete(id);
    }
}
