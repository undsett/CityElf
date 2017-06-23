package com.cityelf.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

  @Id
  private long id;
  @Column(name = "email")
  private String email;
  @Column(name = "phone")
  private String phone;
  @Column(name = "password")
  private String password;
  @Embedded
  private Notification notification;
  @Column(name = "token")
  private String token;
  @Column(name = "expiration_date")
  private LocalDateTime expirationDate;
  @Column(name = "activated")
  private boolean activated;
  @Column(name = "authorized")
  private String authorized;
  @Column(name = "firebase_id")
  private String firebaseId;

  public User(String email, String password, String firebaseId) {
    this.email = email;
    this.phone = null;
    this.password = password;
    this.notification = notification;
    this.token = null;
    this.expirationDate = null;
    this.activated = false;
    this.authorized = "0";
    this.firebaseId = firebaseId;
  }



  public User() {
    this.email = null;
    this.phone = null;
    this.password = null;
    this.activated = false;
    this.token = null;
    this.expirationDate = null;
    this.notification = new Notification();
    this.authorized = "n/a";
    this.firebaseId = null;
  }

  public String getAuthorized() {
    return authorized;
  }

  public void setAuthorized(String authorized) {
    this.authorized = authorized;
  }

  public String getFirebaseId() {
    return firebaseId;
  }

  public void setFirebaseId(String firebaseId) {
    this.firebaseId = firebaseId;
  }

  public Notification getNotification() {
    return notification;
  }

  public void setNotification(Notification notification) {
    this.notification = notification;
  }

  public boolean isActivated() {
    return activated;
  }

  public void setActivated(boolean activated) {
    this.activated = activated;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public void setExpirationDate(LocalDateTime localDateTime) {
    this.expirationDate = localDateTime;
  }

  public String getToken() {
    return token;
  }

  public LocalDateTime getExpirationDate() {
    return expirationDate;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPassword() {
    return password;
  }

  public long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }
}