package com.cityelf.repository;

import com.cityelf.model.Address;
import com.cityelf.model.WaterForecast;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface WaterForecastRepository extends CrudRepository<WaterForecast, Long> {

  List<WaterForecast> findByStart(LocalDateTime startTime);

  Optional<WaterForecast> findByStartAndAddress(LocalDateTime startTime, Address address);

  Optional<WaterForecast> findByStartAndAddress_Address(LocalDateTime startTime, String address);

  List<WaterForecast> findByAddress(Address address);

  void deleteWaterForecastsByStart(LocalDateTime startTime);

  List<WaterForecast> findWaterForecastsByStartLessThanEqualAndEstimatedStopGreaterThan(
      LocalDateTime checkStart, LocalDateTime checkEnd);

  @Modifying
  @Query("delete from WaterForecast wf where wf.peopleReport = false")
  void deletePreviousServiceReports();

  @Transactional
  void deleteByStartBefore(LocalDateTime timeOfEntry);
}
