package com.cityelf.service;

import com.cityelf.exceptions.UserNotFoundException;
import com.cityelf.model.Notification;
import com.cityelf.repository.NotificationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

  @Autowired
  private NotificationRepository notificationRepository;

  public Notification getNotification(long id) throws UserNotFoundException {
    return notificationRepository.getNotification(id)
        .orElseThrow(() -> new UserNotFoundException());
  }
}
