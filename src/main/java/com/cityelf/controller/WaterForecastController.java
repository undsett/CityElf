package com.cityelf.controller;

import com.cityelf.exceptions.ForecastAlreadyExistsException;
import com.cityelf.exceptions.ForecastNotFoundException;
import com.cityelf.model.Address;
import com.cityelf.model.WaterForecast;
import com.cityelf.service.WaterForecastService;

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
@RequestMapping("/services/waterforecast")
public class WaterForecastController {

  @Autowired
  private WaterForecastService waterForecastService;

  @RequestMapping("/all")
  public Iterable<WaterForecast> getAll() {
    return waterForecastService.getAll();
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public WaterForecast getWaterForecast(@PathVariable("id") long id)
      throws ForecastNotFoundException {
    return waterForecastService.getForecast(id);
  }

  @RequestMapping(value = "/get", method = RequestMethod.GET)
  public WaterForecast getWaterForecast(@RequestParam(name = "start") String startTime,
      @RequestParam(name = "address") String address) throws ForecastNotFoundException {
    return waterForecastService.getForecast(LocalDateTime.parse(startTime), address);
  }

  @RequestMapping(value = "/getbystart", method = RequestMethod.GET)
  public List<WaterForecast> getForecastsByTime(@RequestParam(name = "start") String startTime) {
    return waterForecastService.getForecastsByTime(LocalDateTime.parse(startTime));
  }

  @RequestMapping(value = "/getaddressesbytime", method = RequestMethod.GET)
  public Set<Address> getAddressesByTime(@RequestParam(name = "start") String startTime) {
    return waterForecastService.getAddressesByTime(LocalDateTime.parse(startTime));
  }

  @RequestMapping(value = "/addnew", method = RequestMethod.POST)
  public void addNewWaterForecast(@RequestBody WaterForecast forecast)
      throws ForecastAlreadyExistsException {
    waterForecastService.addNewWaterForecast(forecast);
  }

  @RequestMapping(value = "/update", method = RequestMethod.PUT)
  public void updateWaterForecast(@RequestBody WaterForecast forecast)
      throws ForecastNotFoundException {
    waterForecastService.updateWaterForecast(forecast);
  }

  @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
  public void deleteWaterForecast(@RequestBody WaterForecast forecast)
      throws ForecastNotFoundException {
    waterForecastService.deleteWaterForecast(forecast);
  }

  @RequestMapping(value = "/deleteallbytime", method = RequestMethod.DELETE)
  public void deleteWaterForecastsByTime(@RequestBody LocalDateTime startTime) {
    waterForecastService.deleteWaterForecastsByTime(startTime);
  }

  @RequestMapping(value = "/getcurrent", method = RequestMethod.GET)
  public List<WaterForecast> getCurrentWaterForecasts() {
    return waterForecastService.getCurrentWaterForecasts(LocalDateTime.now());
  }
}
