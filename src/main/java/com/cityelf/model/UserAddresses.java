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
  private Integer userId;
  @Column(name = "address_id")
  private Integer addressId;

  public UserAddresses(int user_id, Integer address_id) {
    this.userId = user_id;
    this.addressId = address_id;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public Integer getAddressId() {
    return addressId;
  }

  public void setAddressId(Integer addressId) {
    this.addressId = addressId;
  }
}
