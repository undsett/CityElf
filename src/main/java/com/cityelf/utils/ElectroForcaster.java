package com.cityelf.utils;

import com.cityelf.domain.ForcastData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ElectroForcaster {

  @Autowired
  private StreetExtractor streetExtractor;
  @Autowired
  private NumberExtractor numberExtractor;

  private final Pattern rowPattern = Pattern
      .compile("(?<=<tr[^>]{0,500}>).*?(?=<\\/tr>)", Pattern.CASE_INSENSITIVE);
  private final Pattern colomnPattern = Pattern
      .compile("(?<=<td[^>]{0,500}>).*?(?=<\\/td>)", Pattern.CASE_INSENSITIVE);
  private final String anyTagPattern = "<[^>]+>";

  public List<ForcastData> getForcastsData(String webContent) {
    List<String> tableRowList = getTableRaws(webContent);
    return tableRowList.size() == 0 ? Collections.emptyList() : tableRowList
        .stream()
        .flatMap(this::convert)
        .collect(Collectors.toList());
  }

  private List<String> getTableRaws(String content) {
    List<String> rowList = new ArrayList<>();
    Matcher matcher = rowPattern.matcher(content);
    int counter = 0;
    while (matcher.find()) {
      if (counter++ == 0) {
        continue;
      }
      rowList.add(matcher.group());
    }
    return rowList;
  }

  private Stream<ForcastData> convert(String tableRaw) {
    ColumnTuple columnTuple = getColumnTuple(tableRaw);

    return getColumnTuple(tableRaw).isValid()
        ? columnTuple.getForcastDataList().stream()
        : Collections.<ForcastData>emptyList().stream();
  }

  private ColumnTuple getColumnTuple(String tableRow) {
    Matcher matcher = colomnPattern.matcher(tableRow);
    int counter = 0;
    ColumnTuple columnTuple = new ColumnTuple();
    while (matcher.find()) {
      switch (counter++) {
        case 1:
          String startOff = matcher.group().replaceAll(anyTagPattern, "");
          columnTuple.setStartOff(startOff);
          break;
        case 2:
          String endOff = matcher.group().replaceAll(anyTagPattern, "");
          columnTuple.setEndOff(endOff);
          break;
        case 4:
          String addresses = matcher.group().replaceAll(anyTagPattern, "");
          columnTuple.setAddresses(addresses);
          break;
        default:
          continue;
      }
    }
    return columnTuple;
  }


  private class ColumnTuple {

    private final String notOdessaPattern = "Усатово.+";
    private String startOff;
    private String endOff;
    private String addresses;
    private List<ForcastData> forcastDataList = new ArrayList<>();

    public void setStartOff(String startOff) {
      this.startOff = startOff;
    }

    public void setEndOff(String endOff) {
      this.endOff = endOff;
    }

    public void setAddresses(String addresses) {
      this.addresses = addresses;
    }

    public List<ForcastData> getForcastDataList() {
      List<ForcastData> forcastDataList = new ArrayList<>();
      String[] split = addresses.split("одесса[^,]*,");
      for (String item : split) {
        try {
          item = item.replaceAll(notOdessaPattern, "");
          item = item.trim();
          if (item.isEmpty()) {
            continue;
          }
          String streetName = streetExtractor.getStreetName(item);
          Set<String> houseNumbers = numberExtractor.getNumbers(item);

          ForcastData forcastData = new ForcastData();
          forcastData.setStartOff(getStartOff());
          forcastData.setEndOff((getEndOff()));
          forcastData.setAdress(streetName);
          forcastData.setBuildingNumberList(houseNumbers);
          forcastDataList.add(forcastData);
        } catch (Exception ex) {
          ex.printStackTrace();
          continue;
        }
      }
      return forcastDataList;
    }

    public boolean isValid() {
      return !addresses.isEmpty();
    }

    private LocalDateTime getStartOff() {
      return parseToDateTime(startOff);
    }

    private LocalDateTime getEndOff() {
      return parseToDateTime(endOff);
    }

    private LocalDateTime parseToDateTime(String stringDateTime) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
      return LocalDateTime.parse(stringDateTime, formatter);
    }
  }
}
