package com.cityelf.model;

import com.cityelf.exceptions.FeedbackMessageException;

import org.hibernate.validator.constraints.Email;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "feedback")
public class FeedbackMessage {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private long id;

  @Column(name = "customer")
  private String customer;

  @Column(name = "email")
  @Email(message = "invalid email format",
      regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
          + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
  private String email;

  @Column(name = "theme")
  private String theme;

  @Column(name = "message")
  private String message;

  @Column(name = "date")
  private LocalDateTime messageDate;

  @Column(name = "processed")
  private boolean processed;

  @Transient
  private int maxMessageLength = 1000;

  @Transient
  private int maxThemeLength = 255;

  @Transient
  private int maxCustomerLength = 100;

  @Transient
  private int maxEmailLength = 100;

  public FeedbackMessage() {
    this("", "", "", "");
  }

  public FeedbackMessage(String customer, String email,
      String theme, String message) {
    this.customer = customer;
    this.email = email;
    this.theme = theme;
    this.message = message;
    this.messageDate = LocalDateTime.now();
    this.processed = false;
  }

  public long getId() {
    return id;
  }

  private void setId(long id) {
    this.id = id;
  }

  public String getCustomer() {
    return customer;
  }

  public void setCustomer(String customer) {
    this.customer = customer;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getTheme() {
    return theme;
  }

  public void setTheme(String theme) {
    this.theme = theme;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public boolean isProcessed() {
    return processed;
  }

  public void setProcessed(boolean processed) {
    this.processed = processed;
  }

  public LocalDateTime getMessageDate() {
    return messageDate;
  }

  public String createContent() throws FeedbackMessageException {
    this.checkDataLength();
    return String.format("Feedback message at %s\n\nFrom: %s (email: %s)\n\nTheme: %s\n\n%s",
        this.messageDate.toString(), this.customer, this.email, this.theme, this.message);
  }

  public void checkDataLength() throws FeedbackMessageException {
    String messageFormat = "%s length is invalid. It should be between %d and %d symbols";
    if (this.customer == null || this.customer.length() < 1
        || this.customer.length() > maxCustomerLength) {
      throw new FeedbackMessageException(
          String.format(messageFormat, "Customer name", 1, maxCustomerLength));
    }
    if (this.email == null || this.email.length() < 1
        || this.email.length() > maxEmailLength) {
      throw new FeedbackMessageException(
          String.format(messageFormat, "Email", 1, maxEmailLength));
    }
    if (!this.email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
      throw new FeedbackMessageException("Email is invalid");
    }
    if (this.theme == null || this.theme.length() < 1
        || this.theme.length() > maxThemeLength) {
      throw new FeedbackMessageException(
          String.format(messageFormat, "Theme", 1, maxThemeLength));
    }
    if (this.message == null || this.message.length() < 10
        || this.message.length() > maxMessageLength) {
      throw new FeedbackMessageException(
          String.format(messageFormat, "Message", 10, maxMessageLength));
    }
  }
}
