package com.cityelf.controller;

import com.cityelf.exceptions.ForecastAlreadyExistsException;
import com.cityelf.exceptions.ForecastNotFoundException;
import com.cityelf.model.Address;
import com.cityelf.model.GasForecast;
import com.cityelf.service.GasForecastService;

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
@RequestMapping("/services/gasforecast")
public class GasForecastController {

  @Autowired
  private GasForecastService gasForecastService;

  @RequestMapping("/all")
  public Iterable<GasForecast> getAll() {
    return gasForecastService.getAll();
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public GasForecast getGasForecast(@PathVariable("id") long id) throws ForecastNotFoundException {
    return gasForecastService.getForecast(id);
  }

  @RequestMapping(value = "/get", method = RequestMethod.GET)
  public GasForecast getGasForecast(@RequestParam(name = "start") String startTime,
      @RequestParam(name = "address") String address) throws ForecastNotFoundException {
    return gasForecastService.getForecast(LocalDateTime.parse(startTime), address);
  }

  @RequestMapping(value = "/getbystart", method = RequestMethod.GET)
  public List<GasForecast> getForecastsByTime(@RequestParam(name = "start") String startTime) {
    return gasForecastService.getForecastsByTime(LocalDateTime.parse(startTime));
  }

  @RequestMapping(value = "/getaddressesbytime", method = RequestMethod.GET)
  public Set<Address> getAddressesByTime(@RequestParam(name = "start") String startTime) {
    return gasForecastService.getAddressesByTime(LocalDateTime.parse(startTime));
  }

  @RequestMapping(value = "/addnew", method = RequestMethod.POST)
  public void addNewGasForecast(@RequestBody GasForecast forecast)
      throws ForecastAlreadyExistsException {
    gasForecastService.addNewGasForecast(forecast);
  }

  @RequestMapping(value = "/update", method = RequestMethod.PUT)
  public void updateGasForecast(@RequestBody GasForecast forecast)
      throws ForecastNotFoundException {
    gasForecastService.updateGasForecast(forecast);
  }

  @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
  public void deleteGasForecast(@RequestBody GasForecast forecast)
      throws ForecastNotFoundException {
    gasForecastService.deleteGasForecast(forecast);
  }

  @RequestMapping(value = "/deleteallbytime", method = RequestMethod.DELETE)
  public void deleteGasForecastsByTime(@RequestBody LocalDateTime startTime) {
    gasForecastService.deleteGasForecastsByTime(startTime);
  }

  @RequestMapping(value = "/getcurrent", method = RequestMethod.GET)
  public List<GasForecast> getCurrentGasForecasts() {
    return gasForecastService.getCurrentGasForecasts(LocalDateTime.now());
  }
}
