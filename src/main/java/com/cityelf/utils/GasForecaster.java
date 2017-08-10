package com.cityelf.utils;

import com.cityelf.domain.ForcastData;
import com.cityelf.exceptions.GasPageStructureChangedException;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
class GasForecaster {

  private static final Logger logger = LoggerFactory.getLogger(AddressesFiller.class);

  @Autowired
  private StreetExtractor streetExtractor;

  @Autowired
  private NumberExtractor numberExtractor;

  private Pattern addressColumnPattern = Pattern.compile("адрес");

  private Pattern objectColumnPattern = Pattern.compile("об.ект|об.єкт|абонент|потреб");

  private Map<String, String> rawInformative;
  private Set<String> rawForData;

  List<ForcastData> getForecastData(Element pieceOfNews, LocalDate checkedDate)
      throws GasPageStructureChangedException {
    List<ForcastData> result = new ArrayList<>();
    String newsText = pieceOfNews.select("div.news-text-preview").text();
    LocalDate date = ParserGas.transformDate(getDate(newsText, checkedDate));
    List<String> times = getTimes(newsText);
    LocalTime begin = ParserGas.transformTime(times.get(0));
    LocalTime end = ParserGas.transformTime(times.get(1));
    getRawAddresses(pieceOfNews.select("table").first());
    result.addAll(createForcastData(date, begin, end));
    return result;
  }

  private List<ForcastData> createForcastData(LocalDate date, LocalTime start, LocalTime end) {
    List<ForcastData> forcastDataList = new ArrayList<>();
    LocalDateTime startTime = LocalDateTime.of(date, start);
    LocalDateTime endTime = LocalDateTime.of(date, end);
    String raw;
    List<String> dataAddresses = new ArrayList<>(rawForData);
    for (int i = 0; i < dataAddresses.size(); i++) {
      raw = dataAddresses.get(i);
      for (String rawPart : raw.split("([Уу]л\\.)")) {
        if ((!rawPart.isEmpty()) && ((rawPart = rawPart.trim()).length() > 1)) {
          ForcastData forcastData = new ForcastData();
          forcastData.setStartOff(startTime);
          forcastData.setEndOff(endTime);
          forcastData.setRawAdress(rawInformative.get(raw));
          forcastData.setAdress(streetExtractor.getStreetName(rawPart));
          forcastData.setBuildingNumberList(
              numberExtractor.getNumbers(rawPart.replaceAll("[()]", "")));
          forcastDataList.add(forcastData);
        }
      }
    }
    return forcastDataList;
  }

  private String getDate(String newText, LocalDate checkedDate)
      throws GasPageStructureChangedException {
    String result;
    Pattern datePattern = Pattern.compile("(\\d\\d\\.\\d\\d\\.20\\d\\d)");
    Pattern checkedDatePat = Pattern.compile(String.format("(%te %<tB %<tY)", checkedDate));
    Matcher matcher = datePattern.matcher(newText);
    Matcher checkedDateMatcher = checkedDatePat.matcher(newText);
    if (matcher.find()) {
      result = matcher.group();
    } else if (checkedDateMatcher.find()) {
      result = ParserGas.transformDate(checkedDate);
    } else {
      throw new GasPageStructureChangedException("Date not found");
    }
    return result;
  }

  private List<String> getTimes(String newText) throws GasPageStructureChangedException {
    List<String> results = new ArrayList<>();
    Pattern timePattern = Pattern.compile("(([0-2]{0,1}[0-9]):([0-5][0-9]))");
    Matcher matcher = timePattern.matcher(newText);
    while (matcher.find()) {
      results.add(matcher.group());
    }
    if (results.isEmpty()) {
      //throw new GasPageStructureChangedException("Time not found");
      logger.warn("Time not found. Set whole day.",
          new GasPageStructureChangedException("Time not found"));
      results.add("0:01");
      results.add("23:59");
    }
    if (results.size() > 2) {
      throw new GasPageStructureChangedException("This piece of news structure is non-standard");
    }
    return results;
  }

  private int getNeededColumn(Element table, Pattern pattern)
      throws GasPageStructureChangedException {
    int columnNumber = 0;
    Elements rows = table.select("tr");
    Elements columns = rows.get(0).select("td");
    Matcher matcher;
    for (int indexColumn = 0; indexColumn < columns.size(); indexColumn++) {
      matcher = pattern.matcher(columns.get(indexColumn).text().toLowerCase());
      if (!matcher.find()) {
        columnNumber++;
      } else {
        break;
      }
      if (indexColumn == columns.size() - 1) {
        throw new GasPageStructureChangedException("There is no column with " + pattern.toString());
      }
    }
    return (columnNumber < rows.size()) ? columnNumber : -1;
  }

  private void getRawAddresses(Element table)
      throws GasPageStructureChangedException {
    if (table == null) {
      throw new GasPageStructureChangedException("There is no table with addresses on the page");
    }
    int addressColumn = getNeededColumn(table, addressColumnPattern);
    int objectColumn;
    try {
      objectColumn = getNeededColumn(table, objectColumnPattern);
    } catch (GasPageStructureChangedException exc) {
      logger.warn("Objects column not found. Took addresses", exc);
      objectColumn = addressColumn;
    }

    Elements rows = table.select("tr");
    rows.remove(0);
    String[] rawAddr;
    String infoData;
    this.rawInformative = new HashMap<>();
    this.rawForData = new LinkedHashSet<>();

    for (Element row : rows) {
      rawAddr = getRawAddressInRow(row, addressColumn, objectColumn);
      if (rawAddr[0] != null) {
        rawForData.add(rawAddr[0]);
        infoData = (rawInformative.containsKey(rawAddr[0]))
            ? rawInformative.get(rawAddr[0]).concat("\n").concat(rawAddr[1]) : rawAddr[1];
        rawInformative.put(rawAddr[0], infoData);
      }
    }
  }

  private String[] getRawAddressInRow(Element row, int addressColumn, int objectColumn) {
    String[] result = {null, null};
    Elements columns;
    columns = row.select("td");
    if ((columns.size() > addressColumn) && (columns.size() > objectColumn)) {
      result[0] = columns.get(addressColumn).text();
      result[1] = String.format("Адрес: %s - Объекты: %s",
          result[0], columns.get(objectColumn).text());
      if (numberExtractor.getNumbers(result[0]).isEmpty()) {
        result[0] += ", " + numberExtractor.getNumbers(columns.get(objectColumn).text())
            .toString().replace('[', ' ').replace(']', ' ');
      }
    }
    return result;
  }

}