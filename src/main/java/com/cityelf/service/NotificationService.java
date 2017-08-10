package com.cityelf.service;

import com.cityelf.model.Notification;
import com.cityelf.model.User;
import com.cityelf.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotificationService {

  @Autowired
  private UserRepository userRepository;

  public Optional<Notification> getNotification(long id) {
    User user = userRepository.findOne(id);
    if (user != null) {
      Notification notification = user.getNotification();
      return Optional.of(notification);
    }
    return Optional.empty();
  }
}
