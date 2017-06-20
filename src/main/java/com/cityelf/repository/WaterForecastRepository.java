package com.cityelf.repository;

import com.cityelf.model.Address;
import com.cityelf.model.WaterForecast;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface WaterForecastRepository extends CrudRepository<WaterForecast, Long> {

  List<WaterForecast> findByStart(LocalDateTime startTime);

  Optional<WaterForecast> findByStartAndAddress(LocalDateTime startTime, Address address);

  Optional<WaterForecast> findByStartAndAddress_Address(LocalDateTime startTime, String address);

  void deleteWaterForecastsByStart(LocalDateTime startTime);

  List<WaterForecast> findWaterForecastsByStartLessThanEqualAndEstimatedStopGreaterThan(
      LocalDateTime checkStart, LocalDateTime checkEnd);
}
