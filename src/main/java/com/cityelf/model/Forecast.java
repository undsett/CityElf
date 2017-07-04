package com.cityelf.model;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public abstract class Forecast {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "start")
  private LocalDateTime start;

  @Column(name = "estimated_stop")
  private LocalDateTime estimatedStop;

  @OneToOne
  @JoinColumn(name = "address_id")
  private Address address;

  @Column(name = "people_report")
  private boolean peopleReport;

  public Forecast() {
    start = LocalDateTime.of(1900, 1, 1, 0, 0, 0);
    estimatedStop = LocalDateTime.of(1900, 1, 1, 0, 0, 0);
    peopleReport = false;
    address = new Address();
  }

  public Forecast(LocalDateTime start, LocalDateTime estimatedStop, Address address,
      boolean peopleReport) {
    this.id = 0;
    this.start = start;
    this.estimatedStop = estimatedStop;
    this.address = address;
    this.peopleReport = peopleReport;
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

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public boolean isPeopleReport() {
    return peopleReport;
  }

  public void setPeopleReport(boolean peopleReport) {
    this.peopleReport = peopleReport;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Forecast another = (Forecast) obj;
    return Objects.equals(id, another.id)
        && Objects.equals(start, another.start)
        && Objects.equals(estimatedStop, another.estimatedStop)
        && Objects.equals(peopleReport, another.peopleReport)
        && Objects.equals(address, another.address);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, start, estimatedStop, peopleReport, address);
  }
}
