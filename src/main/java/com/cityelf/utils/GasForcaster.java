package com.cityelf.utils;

import com.cityelf.domain.GasForcastData;
import com.cityelf.domain.Place;
import com.cityelf.domain.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Component
class GasForcaster {

  @Autowired
  private NumberExtractor numberExtractor;
  @Autowired
  private StreetExtractor streetExtractor;

  public List<GasForcastData> getForcastsData(Report[] reports) {
    List<GasForcastData> gasDataList = new ArrayList<>();
    for (Report report : reports) {
      for (Place place : report.places) {
        try {
          GasForcastData gasForcastData = new GasForcastData();
          gasForcastData.setAdress(streetExtractor.getStreetName(place.address));
          gasForcastData.setRawAdress(place.address);
          gasForcastData
              .setBuildingNumberList(numberExtractor.getNumbers(place.address));
          gasForcastData.setStartOff(parseToDate(place.startTime));
          gasForcastData.setEndOff(parseToDate(place.endTime));
          gasDataList.add(gasForcastData);
        } catch (Exception ex) {
          ex.printStackTrace();
          continue;
        }
      }
    }
    return gasDataList;
  }

  public LocalDateTime parseToDate(String stringDate) {
    try {
      return DateFormat.getInstance()
          .parse(stringDate)
          .toInstant()
          .atZone(ZoneId.of("UTC+3"))
          .toLocalDateTime();
    } catch (ParseException ex) {
      ex.printStackTrace();
      return null;
    }
  }
}