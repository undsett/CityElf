package com.cityelf.service;

import com.cityelf.exceptions.ForecastAlreadyExistsException;
import com.cityelf.exceptions.ForecastNotFoundException;
import com.cityelf.model.Address;
import com.cityelf.model.WaterForecast;
import com.cityelf.repository.WaterForecastRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

@Service
public class WaterForecastService {

  @Autowired
  private WaterForecastRepository waterForecastRepository;

  /**
   * Get all water forecasts in repository. If we clear repository every few hours, it will return
   * only actual or just ended forecasts.
   *
   * @return an Iterable object
   */
  public Iterable<WaterForecast> getAll() {
    return waterForecastRepository.findAll();
  }

  /**
   * Get water forecast by ID
   *
   * @param id the ID of the water forecast
   * @return returns the WaterForecast object with specified ID (if exists)
   * @throws ForecastNotFoundException if object doesn't exist
   */
  public WaterForecast getForecast(long id) throws ForecastNotFoundException {
    WaterForecast forecast = waterForecastRepository.findOne(id);
    if (forecast == null) {
      throw new ForecastNotFoundException();
    }
    return forecast;
  }

  /**
   * Get forecast by start time and address.
   *
   * @param startTime date and time when forecast starts
   * @param address the address, where forecast will be (column "street" in data base)
   * @return a WaterForecast object (if exists)
   * @throws ForecastNotFoundException if the object doesn't exist
   */
  public WaterForecast getForecast(LocalDateTime startTime, String address)
      throws ForecastNotFoundException {
    return waterForecastRepository.findByStartAndAddress_Address(startTime, address)
        .orElseThrow(ForecastNotFoundException::new);
  }

  /**
   * Get all forecasts, started at signed time.
   *
   * @param startTime date and time when forecast starts
   * @return the List of WaterForecast, started at the specified time
   */
  public List<WaterForecast> getForecastsByTime(LocalDateTime startTime) {
    return waterForecastRepository.findByStart(startTime);
  }

  /**
   * This will add new water forecast to the repository if there is no one with the same start time
   * and address.
   *
   * @param forecast new WaterForecast object to add into repository
   * @throws ForecastAlreadyExistsException if the object with the same start time and address
   * exists
   */
  public void addNewWaterForecast(WaterForecast forecast) throws ForecastAlreadyExistsException {
    if (waterForecastRepository.findByStartAndAddress(forecast.getStart(), forecast.getAddress())
        .isPresent()) {
      throw new ForecastAlreadyExistsException();
    }
    waterForecastRepository.save(forecast);
  }

  /**
   * This will update an existing forecast or will throw an exception if there is no such forecasts
   * in the repo.
   *
   * @param forecast an existed WaterForecast object which we need to update
   * @throws ForecastNotFoundException if there is no object with the same ID in the repository
   */
  public void updateWaterForecast(WaterForecast forecast) throws ForecastNotFoundException {
    if (!waterForecastRepository.exists(forecast.getId())) {
      throw new ForecastNotFoundException();
    }
    waterForecastRepository.save(forecast);
  }

  /**
   * This will delete one forecast from the repository or will throw an exception
   *
   * @param forecast an existed WaterForecast object which we need to remove
   * @throws ForecastNotFoundException if there is no object with the same ID in the repository
   */
  public void deleteWaterForecast(WaterForecast forecast) throws ForecastNotFoundException {
    if (!waterForecastRepository.exists(forecast.getId())) {
      throw new ForecastNotFoundException();
    }
    waterForecastRepository.delete(forecast);
  }

  /**
   * This will delete from the repository all forecasts started at the signed time.
   *
   * @param startTime date and time when forecast starts
   */
  public void deleteWaterForecastsByTime(LocalDateTime startTime) {
    waterForecastRepository.delete(getForecastsByTime(startTime));
  }

  /**
   * This will return a set of addresses, where water forecasts will start at the signed time.
   *
   * @param startTime date and time when forecast starts
   * @return the Set of Addresses, where forecast at the specified time will happen
   */
  public Set<Address> getAddressesByTime(LocalDateTime startTime) {
    return getForecastsByTime(startTime).stream()
        .map(WaterForecast::getAddress)
        .collect(Collectors.toSet());
  }

  /**
   * This will return a List of WaterForecast objects which are current for the specified time
   *
   * @param checkedTime date and time what we are checking
   * @return the List of water forecasts
   */
  public List<WaterForecast> getCurrentWaterForecasts(LocalDateTime checkedTime) {
    return waterForecastRepository
        .findWaterForecastsByStartLessThanEqualAndEstimatedStopGreaterThan(checkedTime,
            checkedTime);
  }

  /**
   * This will rewrite a List of WaterForecast objects which are current
   *
   * @param waterForecasts collection of WaterForecast objects
   * @return void
   */
  @Transactional
  public void rewriteAll(Iterable<WaterForecast> waterForecasts) {
    waterForecastRepository.deleteAllByPeopleReport(false);
    //waterForecastRepository.save(waterForecasts);
  }

  @Transactional
  public void save(Iterable<WaterForecast> waterForecasts) {
    waterForecastRepository.save(waterForecasts);
  }
}
