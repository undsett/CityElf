package com.cityelf.utils.parser.gas.utils;

import com.cityelf.domain.ForcastData;
import com.cityelf.exceptions.GasPageStructureChangedException;
import com.cityelf.utils.NumberExtractor;
import com.cityelf.utils.StreetExtractor;
import com.cityelf.utils.parser.utils.ParserUtils;
import com.cityelf.utils.parser.utils.ParserUtils.TimeSign;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class GasForecaster {

  private static final Logger logger = LoggerFactory.getLogger(GasForecaster.class);

  @Autowired
  private StreetExtractor streetExtractor;

  @Autowired
  private NumberExtractor numberExtractor;

  @Autowired
  private ParserUtils parserUtils;

  @Autowired
  private GasArticleParser gasArticleParser;

  private Pattern addressColumnPattern = Pattern.compile("адрес");

  private Pattern objectColumnPattern = Pattern.compile("об.ект|об.єкт|абонент|потреб");

  private Map<String, String> rawInformative;
  private Set<String> rawForData;

  public List<ForcastData> getForecastData(Element pieceOfNews, LocalDate checkedDate)
      throws GasPageStructureChangedException {
    List<ForcastData> result;
    Elements tables = pieceOfNews.select("table");
    String newsText = pieceOfNews.select("div.news-text-preview").text();
    if (tables.isEmpty()) {
      result = gasArticleParser.getData(newsText, checkedDate);
    } else {
      result = new ArrayList<>();
      Map<TimeSign, LocalDateTime> timeMap = parserUtils.getShutdownTimes(newsText,
          checkedDate);
      getRawAddresses(tables.first());
      result.addAll(createForcastData(timeMap.get(TimeSign.BEGIN), timeMap.get(TimeSign.END)));
    }
    return result;
  }

  private List<ForcastData> createForcastData(LocalDateTime start, LocalDateTime end) {
    List<ForcastData> forcastDataList = new ArrayList<>();
    String raw;
    List<String> dataAddresses = new ArrayList<>(rawForData);
    for (int i = 0; i < dataAddresses.size(); i++) {
      raw = dataAddresses.get(i);
      for (String rawPart : raw.split("([Уу]л\\.)")) {
        if ((!rawPart.isEmpty()) && ((rawPart = rawPart.trim()).length() > 1)) {
          ForcastData forcastData = new ForcastData();
          forcastData.setStartOff(start);
          forcastData.setEndOff(end);
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
      result[1] = String.format("Адрес: %s - Объекты: %s ",
          result[0], columns.get(objectColumn).text());
      if (numberExtractor.getNumbers(result[0]).isEmpty()) {
        result[0] += ", " + numberExtractor.getNumbers(columns.get(objectColumn).text())
            .toString().replace('[', ' ').replace(']', ' ')
            .replace('-','/');
      }
    }
    return result;
  }

}