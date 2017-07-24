package com.cityelf.service;

import com.cityelf.exceptions.AddressException;
import com.cityelf.exceptions.AddressNotPresentException;
import com.cityelf.model.Address;
import com.cityelf.model.ElectricityForecast;
import com.cityelf.model.GasForecast;
import com.cityelf.model.WaterForecast;
import com.cityelf.repository.ElectricityForecastRepository;
import com.cityelf.repository.GasForecastRepository;
import com.cityelf.repository.WaterForecastRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ShutdownsInfoService {

  @Autowired
  private WaterForecastRepository waterForecastRepository;

  @Autowired
  private GasForecastRepository gasForecastRepository;

  @Autowired
  private ElectricityForecastRepository electricityForecastRepository;

  @Autowired
  private AddressService addressService;

  public Map<String, Object> getAllForecasts(String address) throws AddressException {
    Address addressFound = addressService.getAddress(address)
        .orElseThrow(() -> new AddressNotPresentException());
    List<WaterForecast> forecastWater = waterForecastRepository.findByAddress(addressFound);
    List<ElectricityForecast> forecastElectricity = electricityForecastRepository
        .findByAddress(addressFound);
    List<GasForecast> forecastGas = gasForecastRepository
        .findByAddress(addressFound);
    Map<String, Object> forecastMap = new HashMap<>();

    for (Object f : forecastWater) {
      forecastMap.put("Water", f);
    }

    for (Object f : forecastElectricity) {
      forecastMap.put("Electricity", f);
    }

    for (Object f : forecastGas) {
      forecastMap.put("Gas", f);
    }
    return forecastMap;
  }

}
