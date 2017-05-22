package com.cityelf.service;

import com.cityelf.model.Notification;
import com.cityelf.repository.NotificationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotificationService {

  @Autowired
  private NotificationRepository notificationRepository;

  public Optional<Notification> getNotification(long id) {
    return notificationRepository.getNotification(id);
  }
}
