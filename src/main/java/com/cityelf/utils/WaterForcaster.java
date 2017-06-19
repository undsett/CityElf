package com.cityelf.utils;

import com.cityelf.domain.ForcastData;
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
class WaterForcaster {

  @Autowired
  private NumberExtractor numberExtractor;
  @Autowired
  private StreetExtractor streetExtractor;

  public List<ForcastData> getForcastsData(Report[] reports) {
    List<ForcastData> waterDataList = new ArrayList<>();
    for (Report report : reports) {
      for (Place place : report.places) {
        try {
          ForcastData forcastData = new ForcastData();
          forcastData.setAdress(streetExtractor.getStreetName(place.address));
          forcastData.setRawAdress(place.address);
          forcastData
              .setBuildingNumberList(numberExtractor.getNumbers(place.address));
          forcastData.setStartOff(parseToDate(place.startTime));
          forcastData.setEndOff(parseToDate(place.endTime));
          waterDataList.add(forcastData);
        } catch (Exception ex) {
          ex.printStackTrace();
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
    } catch (ParseException ex) {
      ex.printStackTrace();
      return null;
    }
  }
}
