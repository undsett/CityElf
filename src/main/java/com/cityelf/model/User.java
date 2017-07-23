package com.cityelf.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.validator.constraints.Email;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "email")
  @Email(message = "invalid email format",
      regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
          + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
  private String email;
  @Column(name = "phone")
  private String phone;
  @JsonIgnore
  @Column(name = "password")
  private String password;
  @Embedded
  @NotNull
  private Notification notification;
  @Column(name = "token")
  private String token;
  @Column(name = "expiration_date")
  @JsonIgnore
  private LocalDateTime expirationDate;
  @Column(name = "activated")
  @JsonIgnore
  private boolean activated;
  @Column(name = "authorized")
  @JsonIgnore
  private String authorized;
  @Column(name = "firebase_id")
  private String firebaseId;
  @OneToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "user_addresses",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "address_id"))
  private List<Address> addresses;

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
    this.addresses = new ArrayList<>();
  }

  public User(String firebaseId) {
    this.firebaseId = firebaseId;
    this.addresses = new ArrayList<>();
    this.email = null;
    this.phone = null;
    this.password = null;
    this.activated = false;
    this.token = null;
    this.expirationDate = null;
    this.notification = new Notification();
    this.authorized = "n/a";
  }

  public User(String email, String password, String firebaseId) {
    this.email = email;
    this.password = password;
    this.firebaseId = firebaseId;
    this.addresses = new ArrayList<>();
    this.phone = null;
    this.activated = false;
    this.token = null;
    this.expirationDate = null;
    this.notification = new Notification();
    this.authorized = "n/a";
  }

  public List<Address> getAddresses() {
    return addresses;
  }

  public void setAddresses(List<Address> addresses) {
    this.addresses = addresses;
  }

  public void setId(long id) {
    this.id = id;
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

  @Override
  public boolean equals(Object obj) {
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    User another = (User) obj;
    return Objects.equals(id, another.id)
        && Objects.equals(email, another.email)
        && Objects.equals(phone, another.phone)
        && Objects.equals(password, another.password)
        && Objects.equals(authorized, another.authorized)
        && Objects.equals(firebaseId, another.firebaseId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, email, phone, password, authorized, firebaseId);
  }
}