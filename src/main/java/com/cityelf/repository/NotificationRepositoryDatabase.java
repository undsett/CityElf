package com.cityelf.repository;

import com.cityelf.model.Notification;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class NotificationRepositoryDatabase implements NotificationRepository {

  @Override
  public Optional<Notification> getNotification(long id) {
    return null;
  }
}
