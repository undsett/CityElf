package com.cityelf.controller;

import com.cityelf.utils.ForecastCollector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/forecasts")
public class ForecastsController {

  @Autowired
  private ForecastCollector forecastCollector;

  @RequestMapping(value = "/startCollector", method = RequestMethod.GET)
  public void getElectricityForecast() {
    forecastCollector.startForecastCollector();
  }
}
