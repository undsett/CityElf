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
@Table(name = "shutdown_reports")
public class ShutdownReport {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "forecast_type")
  private String forecastType;

  @Column(name = "start")
  private LocalDateTime start;

  @ManyToOne
  @JoinColumn(name = "address_id")
  private Address address;

  @Column(name = "count")
  private int count;

  @Column(name = "added_to_forecasts")
  private boolean addedToForecasts;

  public long getId() {
    return id;
  }

  public String getForecastType() {
    return forecastType;
  }

  public void setForecastType(String forecastType) {
    this.forecastType = forecastType;
  }

  public LocalDateTime getStart() {
    return start;
  }

  public void setStart(LocalDateTime start) {
    this.start = start;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public boolean isAddedToForecasts() {
    return addedToForecasts;
  }

  public void setAddedToForecasts(boolean addedToForecasts) {
    this.addedToForecasts = addedToForecasts;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof ShutdownReport)) {
      return false;
    }
    ShutdownReport that = (ShutdownReport) obj;
    return getId() == that.getId()
        && getCount() == that.getCount()
        && isAddedToForecasts() == that.isAddedToForecasts()
        && Objects.equals(getForecastType(), that.getForecastType())
        && Objects.equals(getStart(), that.getStart())
        && Objects.equals(getAddress(), that.getAddress());
  }

  @Override
  public int hashCode() {
    return Objects
        .hash(getId(), getForecastType(), getStart(), getAddress(), getCount(),
            isAddedToForecasts());
  }

  @Override
  public String toString() {
    return "ShutdownReport{"
        + "id=" + id
        + ", forecastType='" + forecastType + '\''
        + ", start=" + start
        + ", address=" + address
        + ", count=" + count
        + ", addedToForecasts=" + addedToForecasts + '}';
  }
}
