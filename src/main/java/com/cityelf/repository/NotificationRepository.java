package com.cityelf.repository;

import com.cityelf.domain.Notification;
import com.cityelf.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class NotificationRepository {

  @Autowired
  private UserRepository userRepository;

  public Optional<Notification> getNotification(long id) {

    User user = userRepository.findOne(id);
    if (user != null) {
      Notification notification = new Notification(user.isSms(), user.isEmailNotification(),
          user.isPush());
      return Optional.of(notification);
    }
    return Optional.empty();
  }
}
