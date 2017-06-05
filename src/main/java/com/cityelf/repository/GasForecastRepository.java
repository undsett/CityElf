package com.cityelf.repository;

import com.cityelf.model.Address;
import com.cityelf.model.GasForecast;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface GasForecastRepository extends CrudRepository<GasForecast, Long> {
    List<GasForecast> findByStart(LocalDateTime startTime);

    Optional<GasForecast> findByStartAndAddress(LocalDateTime startTime, Address address);

    Optional<GasForecast> findByStartAndAddress_Address(LocalDateTime startTime, String address);

    void deleteWaterForecastsByStart(LocalDateTime startTime);

    List<GasForecast> findWaterForecastsByStartLessThanEqualAndEstimatedStopGreaterThan(
            LocalDateTime checkStart, LocalDateTime checkEnd);
}
