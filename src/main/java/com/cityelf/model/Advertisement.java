package com.cityelf.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "advertisements")
public class Advertisement {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne
  @JoinColumn(name = "address_id")
  private Address address;

  @Column(name = "subject")
  private String subject;

  @Column(name = "description")
  private String description;

  @Column(name = "time_of_entry")
  private LocalDateTime timeOfEntry;

  public Advertisement() {
    this.timeOfEntry = LocalDateTime.now();
  }

  public Advertisement(Address address, String subject, String description) {
    this.address = address;
    this.subject = subject;
    this.description = description;
    this.timeOfEntry = LocalDateTime.now();
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LocalDateTime getTimeOfEntry() {
    return timeOfEntry;
  }

  @Override
  public String toString() {
    return "Advertisement{"
        + "id=" + id
        + ", address=" + address.getAddress()
        + ", subject='" + subject + '\''
        + ", description='" + description + '\''
        + '}';
  }
}
