package com.cityelf.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "addresses")
public class Address {

  @Id
  private long id;

  @Column(name = "street")
  private String address;

  public Address() {
    this.address = "None";
  }

  public Address(String address) {
    this.address = address;
  }

  public long getId() {
    return id;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }
}
