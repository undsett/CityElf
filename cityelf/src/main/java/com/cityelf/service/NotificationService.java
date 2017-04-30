package com.cityelf.service;

import com.cityelf.model.Notification;
import com.cityelf.model.User;
import com.cityelf.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

  @Autowired
  private NotificationRepository notificationRepository;

  public Notification getNotification(User user) {
    return notificationRepository.getNotification(user);
  }
}
