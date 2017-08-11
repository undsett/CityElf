package com.cityelf.utils.parser.gas.utils;

import com.cityelf.domain.ForcastData;
import com.cityelf.exceptions.GasPageStructureChangedException;
import com.cityelf.service.AddressService;
import com.cityelf.service.SuburbService;
import com.cityelf.utils.NumberExtractor;
import com.cityelf.utils.StreetExtractor;
import com.cityelf.utils.parser.utils.ParserUtils;
import com.cityelf.utils.parser.utils.ParserUtils.TimeSign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class GasArticleParser {

  @Autowired
  private SuburbService suburbService;

  @Autowired
  private AddressService addressService;

  @Autowired
  private StreetExtractor streetExtractor;

  @Autowired
  private NumberExtractor numberExtractor;

  @Autowired
  private ParserUtils parserUtils;

  private static final Logger logger = LoggerFactory.getLogger(GasArticleParser.class);

  public List<ForcastData> getData(String article, LocalDate checkedDate)
      throws GasPageStructureChangedException {

    String suburb = getSuburb(article);
    if (suburb != null) {
      //throw new GasNewsInappropriate("This is the piece of news for the suburb."
      //    + " Suburb name: " + suburb);
      logger.warn("This is the piece of news for the suburb. Suburb name: " + suburb);
      //return new ArrayList<>(0);
    }

    List<String> streets = getStreetsWithNumbers(article);
    if (streets == null || streets.isEmpty()) {
      logger.warn("No known streets in this article.");
      return new ArrayList<>(0);
    }

    Map<TimeSign, LocalDateTime> times = parserUtils.getShutdownTimes(article, checkedDate);
    return fillForcastData(times, streets, suburb);
  }

  private List<ForcastData> fillForcastData(Map<TimeSign, LocalDateTime> times,
      List<String> rawStreets, String suburb) {

    List<ForcastData> results = new ArrayList<>();

    ForcastData forcastData;
    for (String street : rawStreets) {
      forcastData = new ForcastData();
      forcastData.setStartOff(times.get(TimeSign.BEGIN));
      forcastData.setEndOff(times.get(TimeSign.END));
      forcastData.setAdress((suburb == null) ? streetExtractor.getStreetName(street)
          : streetExtractor.getStreetName(street) + " [" + suburb + "]");
      forcastData.setBuildingNumberList(numberExtractor.getNumbers(street));
      forcastData.setRawAdress((suburb == null) ? street : suburb + ": " + street);
      results.add(forcastData);
    }
    return results;
  }

  private String getSuburb(String article) {
    List<String> localities = suburbService.getUniqueLocalities();
    for (String locality : localities) {
      if (article.matches("[\\w\\W]*(" + locality + "?[оеую]?[\\s\\pP])[\\w\\W]*")) {
        return locality;
      }
    }
    return null;
  }

  private List<String> getStreets(String article) {
    List<String> streets = addressService.getUniqueStreetsNames();
    List<String> result = new ArrayList<>();
    for (String street : streets) {
      String forMatching = (street.startsWith("В") || street.startsWith("У"))
          ? "([ВУ]" + street.substring(1) + ")" : street;
      Pattern streetPattern = Pattern.compile(forMatching
          .replaceAll("([Пп]роспект)|([Пп]лоща)|([Пп]лощадь)|([Сс]пуск)|([Уу]звіз)|"
              + "(Ген\\.)|(Генерала)|(Ак\\.)|(Академ.?ка)", ""));
      Matcher matcher = streetPattern.matcher(article);
      if (matcher.find()) {
        result.add(matcher.group());
      }
    }
    return result;
  }

  private List<String> getStreetsWithNumbers(String article) {
    List<String> streets = this.getStreets(article);
    if (streets == null || streets.isEmpty()) {
      return null;
    }

    List<Integer> indexes = new ArrayList<>();
    List<String> withAddresses = new ArrayList<>();
    int streetIndex = 0;
    for (String street : streets) {
      streetIndex = article.indexOf(street);
      indexes.add(streetIndex);
    }
    indexes.add(article.indexOf('.', indexes.get(indexes.size() - 1)));
    indexes.sort(Integer::compareTo);
    for (int ind = 0; ind < indexes.size() - 1; ind++) {
      withAddresses.add(article.substring(indexes.get(ind), indexes.get(ind + 1)));
    }
    return withAddresses;
  }

}
