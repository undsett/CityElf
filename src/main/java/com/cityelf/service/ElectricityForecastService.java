package com.cityelf.service;

import com.cityelf.exceptions.ForecastAlreadyExistsException;
import com.cityelf.exceptions.ForecastNotFoundException;
import com.cityelf.model.Address;
import com.cityelf.model.ElectricityForecast;
import com.cityelf.repository.ElectricityForecastRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

@Service
public class ElectricityForecastService {

  @Autowired
  private ElectricityForecastRepository electricityForecastRepository;

  public ElectricityForecastService() {
  }

  public Iterable<ElectricityForecast> getAll() {
    return electricityForecastRepository.findAll();
  }

  public ElectricityForecast getForecast(long id) throws ForecastNotFoundException {
    ElectricityForecast forecast = electricityForecastRepository.findOne(id);
    if (forecast == null) {
      throw new ForecastNotFoundException();
    }
    return forecast;
  }

  public ElectricityForecast getForecast(LocalDateTime startTime, String address)
      throws ForecastNotFoundException {
    return electricityForecastRepository.findByStartAndAddress_Address(startTime, address)
        .orElseThrow(ForecastNotFoundException::new);
  }

  public List<ElectricityForecast> getForecastsByTime(LocalDateTime startTime) {
    return electricityForecastRepository.findByStart(startTime);
  }

  public void addNewElectricityForecast(ElectricityForecast forecast)
      throws ForecastAlreadyExistsException {
    if (electricityForecastRepository
        .findByStartAndAddress(forecast.getStart(), forecast.getAddress()).isPresent()) {
      throw new ForecastAlreadyExistsException();
    }
    electricityForecastRepository.save(forecast);
  }

  public void updateElectricityForecast(ElectricityForecast forecast)
      throws ForecastNotFoundException {
    if (!electricityForecastRepository.exists(forecast.getId())) {
      throw new ForecastNotFoundException();
    }
    electricityForecastRepository.save(forecast);
  }

  public void deleteElectricityForecast(ElectricityForecast forecast)
      throws ForecastNotFoundException {
    if (!electricityForecastRepository.exists(forecast.getId())) {
      throw new ForecastNotFoundException();
    }
    electricityForecastRepository.delete(forecast);
  }

  public void deleteElectricityForecastsByTime(LocalDateTime startTime) {
    electricityForecastRepository.delete(getForecastsByTime(startTime));
  }

  public Set<Address> getAddressesByTime(LocalDateTime startTime) {
    return getForecastsByTime(startTime).stream()
        .map(ElectricityForecast::getAddress)
        .collect(Collectors.toSet());
  }

  public List<ElectricityForecast> getCurrentElectricityForecasts(LocalDateTime checkedTime) {
    return electricityForecastRepository
        .findElectricityForecastsByStartLessThanEqualAndEstimatedStopGreaterThan(checkedTime,
            checkedTime);
  }

  /**
   * This will rewrite a List of ElectricityForecast objects which are current
   *
   * @param electricityForecasts collection of ElectricityForecast objects
   * @return void
   */
  @Transactional
  public void rewriteAll(Iterable<ElectricityForecast> electricityForecasts) {
    electricityForecastRepository.deleteAllByPeopleReport(false);
    electricityForecastRepository.save(electricityForecasts);
  }
}
