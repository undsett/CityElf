package com.cityelf.domain;

import com.cityelf.model.User;

import java.time.LocalDate;

public class TokenForgotPassword {

  private String token;
  private User user;
  private LocalDate expirationDate = newExpirationDate();

  public TokenForgotPassword(String token, User user) {
    this.token = token;
    this.user = user;
    this.expirationDate = newExpirationDate();
  }

  private LocalDate newExpirationDate() {
    LocalDate localDate = LocalDate.now();
    return localDate.plusDays(1);
  }

  public String getToken() {
    return token;
  }

  public LocalDate getExpirationDate() {
    return expirationDate;
  }

  public User getUser() {
    return user;
  }
}