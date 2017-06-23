package com.cityelf.utils;

import com.cityelf.domain.ForcastData;
import com.cityelf.exceptions.ParserUnavailableException;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class ParserGas {

  @Autowired
  private GasLoader loader;

  @Autowired
  private GasForecaster gasForecaster;

  private String newsTheme;

  private LocalDate dateToCheck;

  public void setLoader(GasLoader loader) {
    this.loader = loader;
  }

  public void setGasForecaster(GasForecaster gasForecaster) {
    this.gasForecaster = gasForecaster;
  }

  public ParserGas() {
    this.dateToCheck = LocalDate.now();
    this.newsTheme = "связи с производством";
  }

  public List<ForcastData> getForcastDataList() throws ParserUnavailableException {
    List<ForcastData> result = new ArrayList<>();
    Elements neededNews = loader.getNeededNews(ParserGas.transformDate(dateToCheck),
        newsTheme);
    for (Element pieceOfNews : neededNews) {
      result.addAll(gasForecaster.getForecastData(pieceOfNews));
    }
    return result;
  }

  static String transformDate(LocalDate toTransform) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    return toTransform.format(formatter);
  }

  static LocalDate transformDate(String toTransform) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    return LocalDate.parse(toTransform, formatter);
  }

  static LocalTime transformTime(String time) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    if (time.length() == 4) {
      time = "0" + time;
    }
    return LocalTime.parse(time, formatter);
  }
}
