package com.cityelf.repository;

import com.cityelf.model.NotificationToken;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public interface FirebaseNotificationRepository extends CrudRepository<NotificationToken, Integer> {

  NotificationToken findByTextHash(String notification);

  @Transactional
  void deleteByTimeOfEntryBefore(LocalDateTime timeOfEntry);
}