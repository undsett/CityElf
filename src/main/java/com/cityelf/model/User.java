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
  //private List<String> recentAddresses = new ArrayList<>();

  public User(String email, String password) {
    this.email = email;
    this.phone = null;
    this.password = password;
    this.notification = null;
    this.token = null;
    this.expirationDate = null;
    this.activated = false;
  }

  public User() {
    this.activated = false;
    this.token = null;
    this.expirationDate = null;
    this.activated = false;
    this.notification = new Notification();
  }

  public User(String firstname, String lastname, String email,
      String phone, String password) {
    this.id = 0;
    this.email = email;
    this.phone = phone;
    this.password = password;
    this.activated = false;
    this.token = null;
    this.expirationDate = null;
    this.activated = false;
    this.notification = null;
    this.notification = new Notification();
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

  public void setExpirationDate() {
    this.expirationDate = LocalDateTime.now().plusDays(1);
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