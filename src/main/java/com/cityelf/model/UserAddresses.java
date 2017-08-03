package com.cityelf.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_addresses")
public class UserAddresses {

  @Id
  private long id;
  @Column(name = "user_id")
  private long userId;
  @Column(name = "address_id")
  private long addressId;


  public UserAddresses(long userId, long addressId) {
    this.userId = userId;
    this.addressId = addressId;
  }

  public UserAddresses() {
  }

  public long getId() {
    return id;
  }

  public long getUserId() {
    return userId;
  }

  public long getAddressId() {
    return addressId;
  }
}

