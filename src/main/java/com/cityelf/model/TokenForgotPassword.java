package com.cityelf.model;

import java.util.Calendar;
import java.util.Date;

public class TokenForgotPassword {

  private String token;
  private User user;
  private Date expirationDate = newExpirationDate();

  public TokenForgotPassword(String token, User user) {
    this.token = token;
    this.user = user;
    this.expirationDate = newExpirationDate();
  }

  private Date newExpirationDate() {
    Date date = new Date();
    Calendar instance = Calendar.getInstance();
    instance.setTime(date);
    instance.add(Calendar.DAY_OF_MONTH, 1);
    return instance.getTime();
  }

  public String getToken() {
    return token;
  }

  public Date getExpirationDate() {
    return expirationDate;
  }

  public User getUser() {
    return user;
  }
}