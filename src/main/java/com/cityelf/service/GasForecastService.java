package com.cityelf.service;

import com.cityelf.exceptions.ForecastAlreadyExistsException;
import com.cityelf.exceptions.ForecastNotFoundException;
import com.cityelf.model.Address;
import com.cityelf.model.GasForecast;
import com.cityelf.repository.GasForecastRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

@Service
public class GasForecastService {

  @Autowired
  private GasForecastRepository gasForecastRepository;

  /**
   * Get all gas forecasts in repository. If we clear repository every few hours, it will return
   * only actual or just ended forecasts.
   *
   * @return an Iterable object
   */
  public Iterable<GasForecast> getAll() {
    return gasForecastRepository.findAll();
  }

  /**
   * Get gas forecast by ID
   *
   * @param id the ID of the gas forecast
   * @return returns the GasForecast object with specified ID (if exists)
   * @throws ForecastNotFoundException if object doesn't exist
   */
  public GasForecast getForecast(long id) throws ForecastNotFoundException {
    GasForecast forecast = gasForecastRepository.findOne(id);
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
  public GasForecast getForecast(LocalDateTime startTime, String address)
      throws ForecastNotFoundException {
    return gasForecastRepository.findByStartAndAddress_Address(startTime, address)
        .orElseThrow(ForecastNotFoundException::new);
  }

  /**
   * Get all forecasts, started at signed time.
   *
   * @param startTime date and time when forecast starts
   * @return the List of GasForecast, started at the specified time
   */
  public List<GasForecast> getForecastsByTime(LocalDateTime startTime) {
    return gasForecastRepository.findByStart(startTime);
  }

  /**
   * This method will add new gas forecast to the repository if there is no one with the same start
   * time and address.
   *
   * @param forecast new GasForecast object to add into repository
   * @throws ForecastAlreadyExistsException if the object with the same start time and address
   * exists
   */
  public void addNewGasForecast(GasForecast forecast) throws ForecastAlreadyExistsException {
    if (gasForecastRepository.findByStartAndAddress(forecast.getStart(), forecast.getAddress())
        .isPresent()) {
      throw new ForecastAlreadyExistsException();
    }
    gasForecastRepository.save(forecast);
  }

  /**
   * This method will update an existing forecast or will throw an exception if there is no such
   * forecasts in the repo.
   *
   * @param forecast an existed GasForecast object which we need to update
   * @throws ForecastNotFoundException if there is no object with the same ID in the repository
   */
  public void updateGasForecast(GasForecast forecast) throws ForecastNotFoundException {
    if (!gasForecastRepository.exists(forecast.getId())) {
      throw new ForecastNotFoundException();
    }
    gasForecastRepository.save(forecast);
  }

  /**
   * This method will delete one forecast from the repository or will throw an exception
   *
   * @param forecast an existed GasForecast object which we need to remove
   * @throws ForecastNotFoundException if there is no object with the same ID in the repository
   */
  public void deleteGasForecast(GasForecast forecast) throws ForecastNotFoundException {
    if (!gasForecastRepository.exists(forecast.getId())) {
      throw new ForecastNotFoundException();
    }
    gasForecastRepository.delete(forecast);
  }

  /**
   * This method will delete from the repository all forecasts started at the signed time.
   *
   * @param startTime date and time when forecast starts
   */
  public void deleteGasForecastsByTime(LocalDateTime startTime) {
    gasForecastRepository.delete(getForecastsByTime(startTime));
  }

  /**
   * This method will return a set of addresses, where water forecasts will start at the signed
   * time.
   *
   * @param startTime date and time when forecast starts
   * @return the Set of Addresses, where forecast at the specified time will happen
   */
  public Set<Address> getAddressesByTime(LocalDateTime startTime) {
    return getForecastsByTime(startTime).stream()
        .map(GasForecast::getAddress)
        .collect(Collectors.toSet());
  }

  /**
   * This method will return a List of GasForecast objects which are current for the specified time
   *
   * @param checkedTime date and time what we are checking
   * @return the List of gas forecasts
   */
  public List<GasForecast> getCurrentGasForecasts(LocalDateTime checkedTime) {
    return gasForecastRepository
        .findGasForecastsByStartLessThanEqualAndEstimatedStopGreaterThan(checkedTime, checkedTime);
  }

  /**
   * This will rewrite a List of GasForecast objects which are current
   *
   * @param gasForecasts collection of GasForecast objects
   * @return void
   */
  @Transactional
  public void rewriteAll(Iterable<GasForecast> gasForecasts) {
    gasForecastRepository.deleteAllByPeopleReport(false);
    gasForecastRepository.save(gasForecasts);
  }

  public void save(Iterable<GasForecast> gasForecasts) {
    gasForecastRepository.save(gasForecasts);
  }
}


