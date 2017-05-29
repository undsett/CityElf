package com.cityelf.repository;

import com.cityelf.model.Notification;
import com.cityelf.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Primary
@Repository
public class NotificationRepositoryMemory implements NotificationRepository {

  @Autowired
  private UserRepository userRepository;

  @Override
  public Optional<Notification> getNotification(long id) {
    return userRepository.getUser(id).map(User::getNotification);
  }
}
