package com.cityelf.repository;

import com.cityelf.model.Address;
import com.cityelf.model.ElectricityForecast;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ElectricityForecastRepository extends CrudRepository<ElectricityForecast, Long> {

  List<ElectricityForecast> findByStart(LocalDateTime startTime);

  Optional<ElectricityForecast> findByStartAndAddress(LocalDateTime startTime, Address address);

  Optional<ElectricityForecast> findByStartAndAddress_Address(LocalDateTime startTime,
      String address);

  List<ElectricityForecast> findByAddress(Address address);

  void deleteElectricityForecastByStart(LocalDateTime startTime);

  void deleteAllByPeopleReport(boolean peopleReport);

  List<ElectricityForecast> findElectricityForecastsByStartLessThanEqualAndEstimatedStopGreaterThan(
      LocalDateTime checkStart, LocalDateTime checkEnd);

  @Transactional
  void deleteByStartBefore(LocalDateTime timeOfEntry);
}
