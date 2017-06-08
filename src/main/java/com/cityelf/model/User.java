package com.cityelf.model;

import com.cityelf.domain.Notification;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

  @Id
  private long id;
  @Column(name = "first_name")
  private String firstname;
  @Column(name = "last_name")
  private String lastname;
  @Column(name = "last_name")
  private String address;
  @Column(name = "email")
  private String email;
  @Column(name = "phone")
  private String phone;
  @Column(name = "password")
  private String password;
  @Column(name = "sms_notification")
  private boolean sms;
  @Column(name = "email_notification")
  private boolean emailNotification;
  @Column(name = "push_notification")
  private boolean push = true;
  @Column(name = "token")
  private String token;
  @Column(name = "expiration_date")
  private LocalDate expirationDate;
  //private List<String> recentAddresses = new ArrayList<>();

  public User() {
  }

  public User(long id, String firstname, String lastname, String address, String email,
      String phone) {
    this.id = id;
    this.firstname = firstname;
    this.lastname = lastname;
    this.address = address;
    this.email = email;
    this.phone = phone;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public void setExpirationDate() {
    this.expirationDate = newExpirationDate();
  }

  public String getToken() {
    return token;
  }

  public LocalDate getExpirationDate() {
    return expirationDate;
  }

  private LocalDate newExpirationDate() {
    LocalDate localDate = LocalDate.now();
    return localDate.plusDays(1);
  }

  public boolean isSms() {
    return sms;
  }

  public boolean isEmailNotification() {
    return emailNotification;
  }

  public boolean isPush() {
    return push;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
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

  public Notification getNotification() {
    Notification notification = new Notification(isSms(), isEmailNotification(), isPush());
    return notification;
  }

  /*public void setNotification(Notification notification) {
    this.notification = notification;
  }*/

  /*public List<String> getRecentAddresses() {
    return recentAddresses;
  }*/

  /*public void setRecentAddresses(List<String> recentAddresses) {
    this.recentAddresses = recentAddresses;
  }*/
}