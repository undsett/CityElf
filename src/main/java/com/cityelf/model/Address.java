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
<<<<<<< HEAD

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private long id;
=======
  
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
>>>>>>> 05555bd10359f5aa5136f261f944eafe1a5dcd5a

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

<<<<<<< HEAD
  public void setAddress(String address) {
    this.address = address;
  }
}

=======
    public void setAddress(String address) {
        this.address = address;
    }
}
>>>>>>> 05555bd10359f5aa5136f261f944eafe1a5dcd5a
