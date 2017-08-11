package com.cityelf.utils;

import com.cityelf.domain.ForcastData;
import com.cityelf.exceptions.GasPageStructureChangedException;
import com.cityelf.exceptions.ParserUnavailableException;
import com.cityelf.utils.parser.gas.utils.GasForecaster;
import com.cityelf.utils.parser.gas.utils.GasLoader;
import com.cityelf.utils.parser.utils.ParserUtils;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class ParserGas {

  @Autowired
  private GasLoader loader;

  @Autowired
  private GasForecaster gasForecaster;

  @Autowired
  private ParserUtils parserUtils;

  private static final Logger logger = LoggerFactory.getLogger(ParserGas.class);

  private String newsTheme = "[(связи с производством)|(зв.язку з [(вико)|(рем)|(роб)])]";

  public void setLoader(GasLoader loader) {
    this.loader = loader;
  }

  public void setGasForecaster(GasForecaster gasForecaster) {
    this.gasForecaster = gasForecaster;
  }

  public List<ForcastData> getForcastDataList() throws ParserUnavailableException {
    return getForcastDataList(LocalDate.now());
  }

  public List<ForcastData> getForcastDataList(LocalDate dateToCheck)
      throws ParserUnavailableException {
    List<ForcastData> result = new ArrayList<>();
    String dateToCheckRegex = String.format("((%s)|(%te %<tB %<tY))",
        parserUtils.transformDate(dateToCheck), dateToCheck);
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
}
