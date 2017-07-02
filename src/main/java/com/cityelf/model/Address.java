package com.cityelf.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "addresses")
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private long id;

  @Column(name = "street")
  private String address;

  @Column(name = "street_ua")
  private String addressUa;

  public Address() {
    this.address = "None";
    this.addressUa = "None";
  }

  public Address(String address) {
    this.address = address;
  }

  public Address(String address, String addressUa) {
    this.address = address;
    this.addressUa = addressUa;
  }

  public void setId(long id) {
    this.id = id;
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

  public String getAddressUa() {
    return addressUa;
  }

  public void setAddressUa(String addressUa) {
    this.addressUa = addressUa;
  }

  @Override
  public String toString() {
    return "Address{" +
        "id=" + id +
        ", address='" + address + '\'' +
        ", addressUa='" + addressUa + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }
    Address that = (Address)object;
    return ((this.getAddress().equals(that.getAddress())
        || (this.getAddressUa().equals(that.getAddressUa()))));
  }
}