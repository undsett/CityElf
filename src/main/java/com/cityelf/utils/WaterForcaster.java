package com.cityelf.utils;

import com.cityelf.domain.Place;
import com.cityelf.domain.Report;
import com.cityelf.domain.WaterForcastData;
import com.cityelf.utils.NumberExtractor;
import com.cityelf.utils.StreetExtractor;

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
