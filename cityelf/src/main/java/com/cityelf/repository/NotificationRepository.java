package com.cityelf.repository;

import com.cityelf.model.Notification;
import com.cityelf.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class NotificationRepository {

  @Autowired
  private UserRepository userRepository;

  public Notification getNotification(long id) {
    User innerUser = userRepository.getUser(id);
    if (innerUser != null) {
      return innerUser.getNotification();
    } else {
      return null;
    }
  }
}
