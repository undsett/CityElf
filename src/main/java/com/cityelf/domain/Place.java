package com.cityelf.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Place {

  @JsonProperty("n")
  public int id;
  @JsonProperty("rState")
  public String plan;
  @JsonProperty("sState")
  public String fact;
  @JsonProperty("progress")
  public String progress;
  @JsonProperty("state")
  public String state;
  @JsonProperty("address")
  public String address;
  @JsonProperty("sTime")
  public String startTime;
  @JsonProperty("rTime")
  public String endTime;
}
