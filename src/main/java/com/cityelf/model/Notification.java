package com.cityelf.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Notification {

  @Column(name = "sms_notification")
  private boolean sms;
  @Column(name = "email_notification")
  private boolean email;
  @Column(name = "push_notification")
  private boolean push;

  public Notification(boolean sms, boolean email, boolean push) {
    this.sms = sms;
    this.email = email;
    this.push = push;
  }

  public Notification() {
    this.sms = false;
    this.email = false;
    this.push = true;
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