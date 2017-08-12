package com.cityelf.utils.parser.utils;

import com.cityelf.exceptions.GasPageStructureChangedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ParserUtils {

  private static final Logger logger = LoggerFactory.getLogger(ParserUtils.class);

  public enum TimeSign { BEGIN, END }

  public String transformDate(LocalDate toTransform) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    return toTransform.format(formatter);
  }

  public LocalDate transformDate(String toTransform) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    return LocalDate.parse(toTransform, formatter);
  }

  public LocalTime transformTime(String time) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    if (time.length() == 4) {
      time = "0" + time;
    }
    return LocalTime.parse(time, formatter);
  }

  public Map<TimeSign, LocalDateTime> getShutdownTimes(String article, LocalDate checkedDate)
      throws GasPageStructureChangedException {
    Map<TimeSign, LocalDateTime> shutdownTimes = new HashMap<>();
    List<String> dates = getDates(article, checkedDate);
    List<String> times = getTimes(article);

    shutdownTimes.put(TimeSign.BEGIN, LocalDateTime.of(this.transformDate(dates.get(0)),
        this.transformTime(times.get(0))));
    shutdownTimes.put(TimeSign.END, LocalDateTime.of(this.transformDate(dates.get(1)),
        this.transformTime(times.get(1))));

    return shutdownTimes;
  }

  private List<String> getDates(String articleText, LocalDate checkedDate)
      throws GasPageStructureChangedException {
    List<String> results = new ArrayList<>();
    Pattern datePattern = Pattern.compile("(\\d\\d\\.\\d\\d\\.20\\d\\d)");
    Pattern checkedDatePat = Pattern.compile(String.format("(%te %<tB %<tY)", checkedDate));
    Matcher matcher = datePattern.matcher(articleText);
    Matcher checkedDateMatcher = checkedDatePat.matcher(articleText);
    while (matcher.find()) {
      results.add(matcher.group());
    }
    if (results.isEmpty() && checkedDateMatcher.find()) {
      results.add(this.transformDate(checkedDate));
    }
    if (results.isEmpty()) {
      throw new GasPageStructureChangedException("Date not found");
    }
    if (results.size() == 1) {
      results.add(results.get(0));
    }
    return results;
  }

  private List<String> getTimes(String articleText) {
    List<String> results = new ArrayList<>();
    Pattern timePattern = Pattern.compile("(([0-2]{0,1}[0-9]):([0-5][0-9]))");
    Matcher matcher = timePattern.matcher(articleText);
    while (matcher.find()) {
      results.add(matcher.group());
    }
    if (results.isEmpty()) {
      logger.warn("Time not found. Set whole day.");
      results.add("0:01");
    }
    if (results.size() == 1) {
      results.add("23:59");
    }
    if (results.size() > 2) {
      logger.warn("This piece of news structure is non-standard. Too many times");
      for (int ind = 2; ind < results.size(); ind++) {
        results.remove(ind);
      }
    }
    return results;
  }

}
