package com.cityelf.model;

import java.time.LocalDateTime;
import java.util.Objects;

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
public class GasForecast extends Forecast {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private long id;

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

  @Override
  public int hashCode() {
    return Objects.hash(id, start, estimatedStop, address, peopleReport);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    GasForecast forecast = (GasForecast) obj;
    return Objects.equals(id, forecast)
        && Objects.equals(start, forecast.start)
        && Objects.equals(estimatedStop, forecast.estimatedStop)
        && Objects.equals(address, forecast.address)
        && Objects.equals(peopleReport, forecast.peopleReport);
  }

  @Override
  public String toString() {
    return "GasForecast{"
        + "start=" + start
        + ", estimatedStop=" + estimatedStop
        + ", address=" + address
        + '}';
  }
}
