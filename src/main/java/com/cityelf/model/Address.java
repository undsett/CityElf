package com.cityelf.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
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

  @OneToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "user_addresses",
      joinColumns = @JoinColumn(name = "address_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id"))
  private List<User> users;

  public Address() {
    this.address = "None";
  }

  public Address(String address) {
    this.address = address;
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

  public List<User> getUsers() {
    return users;
  }

  public void setUsers(List<User> users) {
    this.users = users;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Address another = (Address) obj;
    return Objects.equals(id, another.id)
        && Objects.equals(users, another.users)
        && Objects.equals(address, another.address);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, address, users);
  }
}