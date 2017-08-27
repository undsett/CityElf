package com.cityelf.repository;

import com.cityelf.model.Address;
import com.cityelf.model.GasForecast;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface GasForecastRepository extends CrudRepository<GasForecast, Long> {

  List<GasForecast> findByStart(LocalDateTime startTime);

  Optional<GasForecast> findByStartAndAddress(LocalDateTime startTime, Address address);

  Optional<GasForecast> findByStartAndAddress_Address(LocalDateTime startTime, String address);

  List<GasForecast> findByAddress(Address address);

  void deleteGasForecastsByStart(LocalDateTime startTime);

  void deleteAllByPeopleReport(boolean peopleReport);

  List<GasForecast> findGasForecastsByStartLessThanEqualAndEstimatedStopGreaterThan(
      LocalDateTime checkStart, LocalDateTime checkEnd);

  @Transactional
  void deleteByStartBefore(LocalDateTime timeOfEntry);
}
