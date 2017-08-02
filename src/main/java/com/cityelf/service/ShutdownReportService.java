package com.cityelf.service;

import com.cityelf.exceptions.AddressNotPresentException;
import com.cityelf.exceptions.ForecastAlreadyExistsException;
import com.cityelf.exceptions.UserNotFoundException;
import com.cityelf.exceptions.WrongForecastTypeException;
import com.cityelf.model.ElectricityForecast;
import com.cityelf.model.GasForecast;
import com.cityelf.model.ShutdownReport;
import com.cityelf.model.ShutdownReportRequest;
import com.cityelf.model.UserReports;
import com.cityelf.model.WaterForecast;
import com.cityelf.repository.AddressesRepository;
import com.cityelf.repository.ShutdownReportRepository;
import com.cityelf.repository.UserReportsRepository;
import com.cityelf.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

@Service
public class ShutdownReportService {

  @Autowired
  private ShutdownReportRepository shutdownReportRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private AddressesRepository addressesRepository;

  @Autowired
  private UserReportsRepository userReportsRepository;

  @Autowired
  private GasForecastService gasForecastService;

  @Autowired
  private WaterForecastService waterForecastService;

  @Autowired
  private ElectricityForecastService electricityForecastService;

  @Transactional
  public void addNewReport(ShutdownReportRequest shutdownReportRequest)
      throws AddressNotPresentException, UserNotFoundException,
      ForecastAlreadyExistsException, WrongForecastTypeException {
    ShutdownReport shutdownReport = shutdownReportRequest.getShutdownReport();
    long userId = shutdownReportRequest.getUserId();
    if (!addressesRepository.exists(shutdownReport.getAddress().getId())) {
      throw new AddressNotPresentException();
    }
    if (!userRepository.exists(userId)) {
      throw new UserNotFoundException();
    }
    if (!shutdownReportRepository.findByAddress(shutdownReport.getAddress()).isPresent()) {
      shutdownReportRepository.save(shutdownReport);
      addUserToReport(shutdownReport, userId);
    } else {
      ShutdownReport shutdownReportFromDb = shutdownReportRepository
          .findByAddress(shutdownReport.getAddress()).get();
      List<UserReports> usersFromShutdownReport = userReportsRepository
          .findByShutdownReport(shutdownReportFromDb);
      if (Collections.frequency(usersFromShutdownReport.stream().map(UserReports::getUser).collect(
          Collectors.toList()), userRepository.findById(userId).get()) != 0) {
        return;
      }
      if (shutdownReportFromDb.getCount() < 3) {
        shutdownReportFromDb.setCount(shutdownReportFromDb.getCount() + 1);
        shutdownReportRepository.save(shutdownReportFromDb);
        addUserToReport(shutdownReportFromDb, userId);
      } else {
        moveReportToForecastTable(shutdownReportFromDb);
      }
    }

  }

  private void moveReportToForecastTable(ShutdownReport shutdownReportFromDb)
      throws ForecastAlreadyExistsException, WrongForecastTypeException {
    String forecastType = shutdownReportFromDb.getForecastType();
    switch (forecastType) {
      case "Water":
        try {
          waterForecastService.addNewWaterForecast(createWaterForecast(shutdownReportFromDb));
        } catch (ForecastAlreadyExistsException e) {

        }
        userReportsRepository.deleteAllByShutdownReport(shutdownReportFromDb);
        shutdownReportRepository.delete(shutdownReportFromDb);
        break;
      case "Gas":
        try {
          gasForecastService.addNewGasForecast(createGasForecast(shutdownReportFromDb));
        } catch (ForecastAlreadyExistsException e) {

        }
        userReportsRepository.deleteAllByShutdownReport(shutdownReportFromDb);
        shutdownReportRepository.delete(shutdownReportFromDb);
        break;
      case "Electricity":
        try {
          electricityForecastService
              .addNewElectricityForecast(createElectricityForecast(shutdownReportFromDb));
        } catch (ForecastAlreadyExistsException e) {

        }
        userReportsRepository.deleteAllByShutdownReport(shutdownReportFromDb);
        shutdownReportRepository.delete(shutdownReportFromDb);
        break;
      default:
        throw new WrongForecastTypeException();
    }
  }

  private WaterForecast createWaterForecast(ShutdownReport shutdownReport) {
    WaterForecast waterForecast = new WaterForecast();
    waterForecast.setAddress(shutdownReport.getAddress());
    waterForecast.setStart(shutdownReport.getStart());
    waterForecast.setPeopleReport(true);
    return waterForecast;
  }

  private GasForecast createGasForecast(ShutdownReport shutdownReport) {
    GasForecast gasForecast = new GasForecast();
    gasForecast.setAddress(shutdownReport.getAddress());
    gasForecast.setStart(shutdownReport.getStart());
    gasForecast.setPeopleReport(true);
    return gasForecast;
  }

  private ElectricityForecast createElectricityForecast(ShutdownReport shutdownReport) {
    ElectricityForecast electricityForecast = new ElectricityForecast();
    electricityForecast.setAddress(shutdownReport.getAddress());
    electricityForecast.setStart(shutdownReport.getStart());
    electricityForecast.setPeopleReport(true);
    return electricityForecast;
  }

  private void addUserToReport(ShutdownReport shutdownReport, long userId) {
    UserReports userReports = new UserReports(shutdownReport,
        userRepository.findById(userId).get());
    userReportsRepository.save(userReports);
  }
}
