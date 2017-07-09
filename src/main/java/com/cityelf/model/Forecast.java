package com.cityelf.model;

import java.time.LocalDateTime;

public abstract class Forecast {

  public abstract LocalDateTime getEstimatedStop();

  public abstract LocalDateTime getStart();

  public abstract Address getAddress();
}