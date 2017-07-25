package com.cityelf.utils;

import com.cityelf.domain.ForcastData;
import com.cityelf.exceptions.GasPageStructureChangedException;
import com.cityelf.exceptions.ParserUnavailableException;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

  private static final Logger logger = LoggerFactory.getLogger(AddressesFiller.class);

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
    this.newsTheme = "[(связи с производством)|(зв.язку з [(вико)|(рем)|(роб)])]";
  }

  public List<ForcastData> getForcastDataList() throws ParserUnavailableException {
    List<ForcastData> result = new ArrayList<>();
    String dateToCheckRegex = String.format("((%s)|(%te %<tB %<tY))",
        ParserGas.transformDate(dateToCheck), dateToCheck);
    Elements neededNews = loader.getNeededNews(dateToCheckRegex, newsTheme);
    boolean wasExceptions = false;
    for (Element pieceOfNews : neededNews) {
      try {
        result.addAll(gasForecaster.getForecastData(pieceOfNews, dateToCheck));
      } catch (ParserUnavailableException exc) {
        wasExceptions = true;
        logger.error("There was an exception while gas page was parsing: " + exc.getMessage(), exc);
      }
    }
    if (wasExceptions && result.isEmpty()) {
      throw new GasPageStructureChangedException(
          "There was an exceptions while gas page was parsing. Check log file");
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
