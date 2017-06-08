package com.cityelf.domain;

public class Notification {

  private boolean sms;
  private boolean email;
  private boolean push = true;

  public Notification(boolean sms, boolean email, boolean push) {
    this.sms = sms;
    this.email = email;
    this.push = push;
  }

  public Notification() {
  }

  public boolean isSms() {
    return sms;
  }

  public void setSms(boolean sms) {
    this.sms = sms;
  }

  public boolean isEmail() {
    return email;
  }

  public void setEmail(boolean email) {
    this.email = email;
  }

  public boolean isPush() {
    return push;
  }

  public void setPush(boolean push) {
    this.push = push;
  }
}