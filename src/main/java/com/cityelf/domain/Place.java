package com.cityelf.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Place {

  @JsonProperty("n")
  public int id;
  @JsonProperty("rstate")
  public String plan;
  @JsonProperty("sstate")
  public String fact;
  @JsonProperty("progress")
  public String progress;
  @JsonProperty("state")
  public String state;
  @JsonProperty("address")
  public String address;
  @JsonProperty("stime")
  public String startTime;
  @JsonProperty("rtime")
  public String endTime;
}
