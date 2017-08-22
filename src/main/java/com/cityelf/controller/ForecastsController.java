package com.cityelf.controller;

import com.cityelf.service.DataCollectorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/services/forecasts")
public class ForecastsController {

  @Autowired
  private DataCollectorService dataCollectorService;

  @RequestMapping(value = "/startcollector", method = RequestMethod.GET)
  public void startcollector() {
    dataCollectorService.startCollector();
  }
}
