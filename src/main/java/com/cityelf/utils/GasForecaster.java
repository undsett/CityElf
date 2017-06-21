package com.cityelf.utils;

import com.cityelf.domain.ForcastData;
import com.cityelf.exceptions.GasPageStructureChangedException;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
class GasForecaster {

  @Autowired
  private StreetExtractor streetExtractor;

  @Autowired
  private NumberExtractor numberExtractor;

  List<ForcastData> getForecastData(Element pieceOfNews) throws GasPageStructureChangedException {
    List<ForcastData> result = new ArrayList<>();
    String newsText = pieceOfNews.select("div.news-text-preview").text();
    LocalDate date = ParserGas.transformDate(getDate(newsText));
    List<String> times = getTimes(newsText);
    LocalTime begin = ParserGas.transformTime(times.get(0));
    LocalTime end = ParserGas.transformTime(times.get(1));
    Set<String> rawAddresses = getRawAddresses(pieceOfNews.select("table").first());
    result.addAll(createForcastData(date, begin, end, rawAddresses));
    return result;
  }

  private List<ForcastData> createForcastData(LocalDate date, LocalTime start, LocalTime end,
      Set<String> rawAddresses) {
    List<ForcastData> forcastDataList = new ArrayList<>();
    LocalDateTime startTime = LocalDateTime.of(date, start);
    LocalDateTime endTime = LocalDateTime.of(date, end);
    for (String raw : rawAddresses) {
      for (String rawPart : raw.split("([Уу]л\\.)|([Пп]л\\.)|([Пп]р\\.)|([Пп]ер\\.)")) {
        if (!rawPart.isEmpty()) {
          rawPart = rawPart.trim();
          ForcastData forcastData = new ForcastData();
          forcastData.setStartOff(startTime);
          forcastData.setEndOff(endTime);
          forcastData.setRawAdress(raw);
          forcastData.setAdress(streetExtractor.getStreetName(rawPart));
          forcastData.setBuildingNumberList(
              numberExtractor.getNumbers(rawPart.replaceAll("[()]","")));
          forcastDataList.add(forcastData);
        }
      }
    }
    return forcastDataList;
  }

  private String getDate(String newText) throws GasPageStructureChangedException {
    Pattern datePattern = Pattern.compile("\\d\\d\\.\\d\\d\\.20\\d\\d");
    Matcher matcher = datePattern.matcher(newText);
    if (!matcher.find()) {
      throw new GasPageStructureChangedException("Date not found");
    }
    return matcher.group();
  }

  private List<String> getTimes(String newText) throws GasPageStructureChangedException {
    List<String> results = new ArrayList<>();
    Pattern timePattern = Pattern.compile("(([0-2]{0,1}[0-9]):([0-5][0-9]))");
    Matcher matcher = timePattern.matcher(newText);
    while (matcher.find()) {
      results.add(matcher.group());
    }
    if (results.isEmpty()) {
      throw new GasPageStructureChangedException("Time not found");
    }
    if (results.size() > 2) {
      throw new GasPageStructureChangedException("This piece of news structure is non-standard");
    }
    return results;
  }

  private int getAddressColumn(Element table) throws GasPageStructureChangedException {
    int columnNumber = 0;
    Elements rows = table.select("tr");
    Elements columns = rows.get(0).select("td");
    for (int indexColumn = 0; indexColumn < columns.size(); indexColumn++) {
      if (!columns.get(indexColumn).text().toLowerCase().contains("адрес")) {
        columnNumber++;
      } else {
        break;
      }
      if (indexColumn == columns.size() - 1) {
        throw new GasPageStructureChangedException("There is no address column");
      }
    }
    return (columnNumber < rows.size()) ? columnNumber : -1;
  }

  private Set<String> getRawAddresses(Element table) throws GasPageStructureChangedException {
    Set<String> rawAddresses = new HashSet<>();
    int addressColumn = getAddressColumn(table);
    if (addressColumn == - 1) {
      throw new GasPageStructureChangedException("There is no address column");
    }
    Elements rows = table.select("tr");
    rows.remove(0);
    for (Element row : rows) {
      rawAddresses.add(row.select("td").get(addressColumn).text());
    }
    return rawAddresses;
  }
}
