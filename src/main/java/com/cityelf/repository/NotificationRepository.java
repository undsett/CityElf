package com.cityelf.repository;

import com.cityelf.model.Notification;
import com.cityelf.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class NotificationRepository {

  @Autowired
  private UserRepository userRepository;

  public Optional<Notification> getNotification(long id) {
    return userRepository.getUser(id).map(User::getNotification);
  }
}
