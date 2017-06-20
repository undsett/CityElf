package com.cityelf.service;

import com.cityelf.exceptions.ForecastAlreadyExistsException;
import com.cityelf.exceptions.ForecastNotFoundException;
import com.cityelf.model.Address;
import com.cityelf.model.GasForecast;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface GasForecastService {

  Iterable<GasForecast> getAll();

  GasForecast getForecast(long id) throws ForecastNotFoundException;

  GasForecast getForecast(LocalDateTime startTime, String address) throws ForecastNotFoundException;

  List<GasForecast> getForecastsByTime(LocalDateTime startTime);

  void addNewGasForecast(GasForecast forecast) throws ForecastAlreadyExistsException;

  void updateGasForecast(GasForecast forecast) throws ForecastNotFoundException;

  void deleteGasForecast(GasForecast forecast) throws ForecastNotFoundException;

  void deleteGasForecastsByTime(LocalDateTime startTime);

  Set<Address> getAddressesByTime(LocalDateTime startTime);

  List<GasForecast> getCurrentGasForecasts(LocalDateTime checkedTime);
}
