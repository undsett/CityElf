package com.cityelf.service;


import com.cityelf.exceptions.ForecastNotFoundException;
import com.cityelf.model.ElectricityForecast;
import com.cityelf.model.GasForecast;
import com.cityelf.model.WaterForecast;
import com.cityelf.repository.ElectricityForecastRepository;
import com.cityelf.repository.GasForecastRepository;
import com.cityelf.repository.WaterForecastRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShutdownsInfoService {

  @Autowired
  private WaterForecastRepository waterForecastRepository;

  @Autowired
  private GasForecastRepository gasForecastRepository;

  @Autowired
  private ElectricityForecastRepository electricityForecastRepository;

  public ArrayList getAllForecasts(LocalDateTime startTime, String address)
       {
    Optional<WaterForecast> forecastWater = waterForecastRepository.findByStartAndAddress_Address(
        startTime, address);
    Optional<ElectricityForecast> forecastElectricity = electricityForecastRepository.findByStartAndAddress_Address(startTime, address);
    Optional<GasForecast> forecastGas = gasForecastRepository.findByStartAndAddress_Address(startTime, address);
    ArrayList forecastList = new ArrayList();

    forecastList.add(forecastWater);
    forecastList.add(forecastElectricity);
    forecastList.add(forecastGas);
    return forecastList;
  }

}
