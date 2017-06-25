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

  @Column(name = "estimated_stop")
  private LocalDateTime estimatedStop;

  @Column(name = "people_report")
  private boolean peopleReport;

  @ManyToOne
  @JoinColumn(name = "address_id")
  private Address address;

  public void setId(long id) {
    this.id = id;
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
