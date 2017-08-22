package com.cityelf.controller;

import com.cityelf.exceptions.ForecastAlreadyExistsException;
import com.cityelf.exceptions.ForecastNotFoundException;
import com.cityelf.model.Address;
import com.cityelf.model.ElectricityForecast;
import com.cityelf.service.ElectricityForecastService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/services/electricityforecast")
public class ElectricityForecastController {

  @Autowired
  private ElectricityForecastService electricityForecastService;

  @RequestMapping("/all")
  public Iterable<ElectricityForecast> getAll() {
    return electricityForecastService.getAll();
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ElectricityForecast getElectricityForecast(@PathVariable("id") long id)
      throws ForecastNotFoundException {
    return electricityForecastService.getForecast(id);
  }

  @RequestMapping(value = "/get", method = RequestMethod.GET)
  public ElectricityForecast getElectricityForecast(
      @RequestParam(name = "start") String startTime,
      @RequestParam(name = "address") String address) throws ForecastNotFoundException {
    return electricityForecastService.getForecast(LocalDateTime.parse(startTime), address);
  }

  @RequestMapping(value = "/getbystart", method = RequestMethod.GET)
  public List<ElectricityForecast> getForecastsByTime(
      @RequestParam(name = "start") String startTime) {
    return electricityForecastService.getForecastsByTime(LocalDateTime.parse(startTime));
  }

  @RequestMapping(value = "/getaddressesbytime", method = RequestMethod.GET)
  public Set<Address> getAddressesByTime(@RequestParam(name = "start") String startTime) {
    return electricityForecastService.getAddressesByTime(LocalDateTime.parse(startTime));
  }

  @RequestMapping(value = "/addnew", method = RequestMethod.POST)
  public void addNewElectricityForecast(@RequestBody ElectricityForecast forecast)
      throws ForecastAlreadyExistsException {
    electricityForecastService.addNewElectricityForecast(forecast);
  }

  @RequestMapping(value = "/update", method = RequestMethod.PUT)
  public void updateElectricityForecast(@RequestBody ElectricityForecast forecast)
      throws ForecastNotFoundException {
    electricityForecastService.updateElectricityForecast(forecast);
  }

  @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
  public void deleteElectricityForecast(@RequestBody ElectricityForecast forecast)
      throws ForecastNotFoundException {
    electricityForecastService.deleteElectricityForecast(forecast);
  }

  @RequestMapping(value = "/deleteallbytime", method = RequestMethod.DELETE)
  public void deleteElectricityForecastsByTime(@RequestBody LocalDateTime startTime) {
    electricityForecastService.deleteElectricityForecastsByTime(startTime);
  }

  @RequestMapping(value = "/getcurrent", method = RequestMethod.GET)
  public List<ElectricityForecast> getCurrentElectricityForecasts() {
    return electricityForecastService.getCurrentElectricityForecasts(LocalDateTime.now());
  }
}
