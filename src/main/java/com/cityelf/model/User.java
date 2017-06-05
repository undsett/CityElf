package com.cityelf.model;

import java.util.ArrayList;
import java.util.List;

public class User {

  private long id;
  private String firstname;
  private String lastname;
  private String address;
  private String email;
  private String phone;
  private String password;
  private Notification notification;
  private List<String> recentAddresses = new ArrayList<>();

  public User() {
    id = 0;
    firstname = "None";
    lastname = "None";
    address = "None";
    email = "None";
    phone = "None";
    password = "None";
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
    return notification;
  }

  public void setNotification(Notification notification) {
    this.notification = notification;
  }

  public List<String> getRecentAddresses() {
    return recentAddresses;
  }

  public void setRecentAddresses(List<String> recentAddresses) {
    this.recentAddresses = recentAddresses;
  }
}