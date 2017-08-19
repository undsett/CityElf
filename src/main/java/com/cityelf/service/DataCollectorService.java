package com.cityelf.service;

import com.cityelf.domain.ForcastData;
import com.cityelf.exceptions.ParserUnavailableException;
import com.cityelf.model.Address;
import com.cityelf.model.ElectricityForecast;
import com.cityelf.model.Forecast;
import com.cityelf.model.GasForecast;
import com.cityelf.model.WaterForecast;
import com.cityelf.utils.ParserElectro;
import com.cityelf.utils.ParserGas;
import com.cityelf.utils.ParserWater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DataCollectorService {

  private final Logger logger = LoggerFactory.getLogger(getClass());
  @Autowired
  private ElectricityForecastService electricityForecastService;
  @Autowired
  private WaterForecastService waterForecastService;
  @Autowired
  private GasForecastService gasForecastService;
  @Autowired
  private FirebaseNotificationService firebaseNotificationService;
  @Autowired
  private AddressService addressService;

  @Autowired
  private ParserElectro parserElectro;
  @Autowired
  private ParserWater parserWater;
  @Autowired
  private ParserGas parserGas;


  public void startCollector() {
    Set<Forecast> forecasts = getAllForecasts();
    databaseRewriteAll(forecasts);
    firebaseNotificationService.firebaseNotificate(forecasts);
  }

  private Set<Forecast> getAllForecasts() {
    Set<Forecast> forecasts = new HashSet<>();
    List<ForcastData> forcastDataList = Collections.EMPTY_LIST;

    try {
      forcastDataList = parserWater.getForcastDataList();
      for (ForcastData forcastData : forcastDataList) {
        logger.trace(forcastData.toString());
        Collection<Address> addresses = addressService
            .getAddresses(forcastData.getAdress(), forcastData.getBuildingNumberList());
        for (Address address : addresses) {
          WaterForecast forecast = new WaterForecast();
          forecast.setStart(forcastData.getStartOff());
          forecast.setEstimatedStop(forcastData.getEndOff());
          forecast.setAddress(address);
          logger.trace(forecast.toString());
          forecasts.add(forecast);
        }
      }
    } catch (ParserUnavailableException ex) {
      logger.error("WaterParser unavailable", ex);
    } catch (Exception ex) {
      logger.error("WaterParser ERROR", ex);
    }

    try {
      forcastDataList = parserElectro.getForcastDataList();
      for (ForcastData forcastData : forcastDataList) {
        logger.trace(forcastData.toString());
        Collection<Address> addresses = addressService
            .getAddresses(forcastData.getAdress(), forcastData.getBuildingNumberList());
        for (Address address : addresses) {
          ElectricityForecast forecast = new ElectricityForecast();
          forecast.setStart(forcastData.getStartOff());
          forecast.setEstimatedStop(forcastData.getEndOff());
          forecast.setAddress(address);
          logger.trace(forecast.toString());
          forecasts.add(forecast);
        }
      }
    } catch (ParserUnavailableException ex) {
      logger.error("ElectricityParser unavailable", ex);
    } catch (Exception ex) {
      logger.error("ElectricityParser ERROR", ex);
    }

    try {
      forcastDataList = parserGas.getForcastDataList();
      for (ForcastData forcastData : forcastDataList) {
        logger.trace(forcastData.toString());
        Collection<Address> addresses = addressService
            .getAddresses(forcastData.getAdress(), forcastData.getBuildingNumberList());
        for (Address address : addresses) {
          GasForecast forecast = new GasForecast();
          forecast.setStart(forcastData.getStartOff());
          forecast.setEstimatedStop(forcastData.getEndOff());
          forecast.setAddress(address);
          logger.trace(forecast.toString());
          forecasts.add(forecast);
        }
      }
    } catch (ParserUnavailableException ex) {
      logger.error("GasParser unavailable", ex);
    } catch (Exception ex) {
      logger.error("GasParser ERROR", ex);
    }
    return forecasts;
  }

  private void databaseRewriteAll(Set<Forecast> forecasts) {
    try {
      List<GasForecast> gasForecasts = new ArrayList<>();
      List<ElectricityForecast> electricityForecasts = new ArrayList<>();
      List<WaterForecast> waterForecasts = new ArrayList<>();

      for (Forecast forecast : forecasts) {
        if (forecast instanceof ElectricityForecast) {
          electricityForecasts.add(((ElectricityForecast) forecast));
        } else if (forecast instanceof WaterForecast) {
          waterForecasts.add(((WaterForecast) forecast));
        } else {
          gasForecasts.add(((GasForecast) forecast));
        }
      }
      if (electricityForecasts.size() > 0) {
        electricityForecastService.rewriteAll(electricityForecasts);
      }
      if (waterForecasts.size() > 0) {
        waterForecastService.rewriteAll(waterForecasts);
        waterForecastService.save(waterForecasts);
      }
      if (gasForecasts.size() > 0) {
        gasForecastService.rewriteAll(gasForecasts);
      }
    } catch (Exception ex) {
      logger.error("Error while forecasts saving", ex);
    }
  }

}
