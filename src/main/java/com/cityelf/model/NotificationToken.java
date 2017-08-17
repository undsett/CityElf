package com.cityelf.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "notification")
public class NotificationToken {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "notification")
  private String textHash;

  @Column(name = "notification_time")
  private LocalDateTime timeOfEntry;

  public NotificationToken() {
  }

  public NotificationToken(String street, LocalDateTime begin, LocalDateTime end, long userId) {
    this.timeOfEntry = LocalDateTime.now();
    if (begin == null) {
      begin = LocalDateTime.now();
    }
    if (end == null) {
      end = LocalDateTime.now().plusHours(7);
    }
    this.textHash = street + begin.toString() + end.toString() + userId;
  }

  public String getTextHash() {
    return textHash;
  }

  public LocalDateTime getTimeOfEntry() {
    return timeOfEntry;
  }
}