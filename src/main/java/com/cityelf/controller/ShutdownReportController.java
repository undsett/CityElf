package com.cityelf.controller;

import com.cityelf.exceptions.AddressNotPresentException;
import com.cityelf.exceptions.ForecastAlreadyExistsException;
import com.cityelf.exceptions.Status;
import com.cityelf.exceptions.UserNotFoundException;
import com.cityelf.exceptions.WrongForecastTypeException;
import com.cityelf.model.ShutdownReport;
import com.cityelf.model.ShutdownReportRequest;
import com.cityelf.service.ShutdownReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/services/peoplereport")
public class ShutdownReportController {

  @Autowired
  private ShutdownReportService shutdownReportService;

  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public Status addNewReport(@RequestBody ShutdownReportRequest shutdownReportRequest)
      throws AddressNotPresentException, ForecastAlreadyExistsException,
      WrongForecastTypeException, UserNotFoundException {
    return shutdownReportService.addNewReport(shutdownReportRequest);
  }

  @RequestMapping(value = "/getall", method = RequestMethod.GET)
  public Iterable<ShutdownReport> getAll() {
    return shutdownReportService.getAll();
  }
}
