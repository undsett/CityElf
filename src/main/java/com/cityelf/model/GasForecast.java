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
@Table(name = "gas_forecasts")
public class GasForecast {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  long id;

  @Column(name = "start")
  private LocalDateTime start;

  @Column(name = "estimatedStop")
  private LocalDateTime estimatedStop;

  @Column(name = "address_id")
  private long addressId;

  @Column(name = "peopleReport")
  private boolean peopleReport;

  @ManyToOne
  @JoinColumn(name = "address_id")
  private Address address;

  public GasForecast() {
    start = LocalDateTime.of(1900, 1, 1, 0, 0, 0);
    estimatedStop = LocalDateTime.of(1900, 1, 1, 0, 0, 0);
    peopleReport = false;
    address = new Address();
  }

  public GasForecast(LocalDateTime start, LocalDateTime estimatedStop, boolean peopleReport,
      Address address) {
    this.id = 0;
    this.start = start;
    this.estimatedStop = estimatedStop;
    this.peopleReport = peopleReport;
    this.address = address;
  }

  public long getId() {
    return id;
  }

  public LocalDateTime getStart() {
    return start;
  }

  public void setStart(LocalDateTime start) {
    this.start = start;
  }

  public LocalDateTime getEstimatedStop() {
    return estimatedStop;
  }

  public void setEstimatedStop(LocalDateTime estimatedStop) {
    this.estimatedStop = estimatedStop;
  }

  public long getAddressId() {
    return addressId;
  }

  public void setAddressId(long addressId) {
    this.addressId = addressId;
  }

  public boolean isPeopleReport() {
    return peopleReport;
  }

  public void setPeopleReport(boolean peopleReport) {
    this.peopleReport = peopleReport;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }


}
