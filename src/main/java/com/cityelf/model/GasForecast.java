package com.cityelf.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "gas_forecasts")
public class GasForecast extends Forecast {
}