package com.cityelf.repository;

import com.cityelf.model.Notification;

import java.util.Optional;

public interface NotificationRepository {

  Optional<Notification> getNotification(long id);
}
