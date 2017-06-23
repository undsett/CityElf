package com.cityelf.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "water_forecasts")
public class WaterForecast {

  @Id
  private long id;

  @Column(name = "start")
  private LocalDateTime start;

  @Column(name = "estimated_stop")
  private LocalDateTime estimatedStop;

  @ManyToOne
  @JoinColumn(name = "address_id")
  private Address address;

  @Column(name = "people_report")
  private boolean peopleReport;

  public WaterForecast() {
    start = LocalDateTime.of(1900, 1, 1, 0, 0, 0);
    estimatedStop = LocalDateTime.of(1900, 1, 1, 0, 0, 0);
    peopleReport = false;
    address = new Address();
  }

  public WaterForecast(LocalDateTime start, LocalDateTime estimatedStop, Address address,
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
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    WaterForecast that = (WaterForecast) obj;

    if (id != that.id) {
      return false;
    }
    if (peopleReport != that.peopleReport) {
      return false;
    }
    if (start != null ? !start.equals(that.start) : that.start != null) {
      return false;
    }
    if (estimatedStop != null ? !estimatedStop.equals(that.estimatedStop)
        : that.estimatedStop != null) {
      return false;
    }
    return address != null ? address.equals(that.address) : that.address == null;

  }

  @Override
  public int hashCode() {
    int result = (int) (id ^ (id >>> 32));
    result = 31 * result + (start != null ? start.hashCode() : 0);
    result = 31 * result + (estimatedStop != null ? estimatedStop.hashCode() : 0);
    result = 31 * result + (address != null ? address.hashCode() : 0);
    result = 31 * result + (peopleReport ? 1 : 0);
    return result;
  }
}
