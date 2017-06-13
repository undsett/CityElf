package com.cityelf.model.water.parser;

import java.time.LocalDateTime;
import java.util.Set;

public class WaterForcastData {

  private LocalDateTime startOff;
  private LocalDateTime endOff;
  private String adress;
  private String rawAdress;
  private Set<String> buildingNumberList;

  public LocalDateTime getStartOff() {
    return startOff;
  }

  /**
   * Return water shut-off start time
   *
   * @return LocalDateTime
   */
  public void setStartOff(LocalDateTime startOff) {
    this.startOff = startOff;
  }

  /**
   * Return estimated time when water supply will resume
   *
   * @return LocalDateTime or null if time is undefined
   */
  public LocalDateTime getEndOff() {
    return endOff;
  }

  public void setEndOff(LocalDateTime endOff) {
    this.endOff = endOff;
  }


  /**
   * Return the street that is under water shut-off
   *
   * @return string of street name
   */
  public String getAdress() {
    return adress;
  }

  public void setAdress(String adress) {
    this.adress = adress;
  }

  /**
   * Return raw message as it is on the website of infox
   *
   * @return string of raw message
   */
  public String getRawAdress() {
    return rawAdress;
  }

  public void setRawAdress(String rawAdress) {
    this.rawAdress = rawAdress;
  }

  /**
   * Return building numbers on the current street for water shut-off
   *
   * @return set of some building numbers(or range of numbers) for Water shut-off or empty set if
   * every buildings on the street is under shut-off
   */
  public Set<String> getBuildingNumberList() {
    return buildingNumberList;
  }

  public void setBuildingNumberList(Set<String> buildingNumberList) {
    this.buildingNumberList = buildingNumberList;
  }

  @Override
  public String toString() {
    return "WaterForcastData{"
        +
        "startOff=" + startOff
        +
        ", endOff=" + endOff
        +
        ", adress='" + adress + '\''
        +
        ", rawAdress='" + rawAdress + '\''
        +
        ", buildingNumberList=" + buildingNumberList
        +
        '}';
  }
}
