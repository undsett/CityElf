package com.cityelf.model;

public class User {

  private String firstname;
  private String lastname;
  private String adress;
  private String email;
  private String phone;


  public User() {
    this.firstname = "None";
    this.lastname = "None";
    this.adress = "None";
    this.email = "None";
    this.phone = "None";
  }

  public User(String firstname, String lastname, String adress, String email, String phone) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.adress = adress;
    this.email = email;
    this.phone = phone;
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

  public String getAdress() {
    return adress;
  }

  public void setAdress(String adress) {
    this.adress = adress;
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
