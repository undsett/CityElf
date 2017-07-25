package com.cityelf.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "admin_osmd_address")
public class OsmdAdminAddresses {

  @Id
  @Column(name = "user_admin_id")
  private long userAdminId;
  @Column(name = "address_id")
  private long addressId;

  public OsmdAdminAddresses() {
  }

  public OsmdAdminAddresses(long userAdminId, long addressId) {
    this.userAdminId = userAdminId;
    this.addressId = addressId;
  }

  public long getUserAdminId() {
    return userAdminId;
  }

  public void setUserAdminId(long userAdminId) {
    this.userAdminId = userAdminId;
  }

  public long getAddressId() {
    return addressId;
  }

  public void setAddressId(long addressId) {
    this.addressId = addressId;
  }
}
