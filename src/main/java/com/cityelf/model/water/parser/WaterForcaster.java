package com.cityelf.model.water.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Component
class WaterForcaster {

  @Autowired
  private NumberExtractor numberExtractor;
  @Autowired
  private StreetExtractor streetExtractor;

  public List<WaterForcastData> getForcastsData(Report[] reports) {
    List<WaterForcastData> waterDataList = new ArrayList<>();
    for (Report report : reports) {
      for (Place place : report.places) {
        try {
          WaterForcastData waterForcastData = new WaterForcastData();
          waterForcastData.setAdress(streetExtractor.getStreetName(place.address));
          waterForcastData.setRawAdress(place.address);
          waterForcastData
              .setBuildingNumberList(numberExtractor.getNumbers(place.address));
          waterForcastData.setStartOff(parseToDate(place.startTime));
          waterForcastData.setEndOff(parseToDate(place.endTime));
          waterDataList.add(waterForcastData);
        } catch (Exception exeption) {
          exeption.printStackTrace();
          continue;
        }
      }
    }
    return waterDataList;
  }

  public LocalDateTime parseToDate(String stringDate) {
    try {
      return DateFormat.getInstance()
          .parse(stringDate)
          .toInstant()
          .atZone(ZoneId.of("UTC+3"))
          .toLocalDateTime();
    } catch (ParseException exception) {
      exception.printStackTrace();
      return null;
    }
  }
}
