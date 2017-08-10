package com.cityelf.utils.address.finder.utils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class BuildingNumberExtender {

  private Logger logger = LogManager.getLogger(getClass());
  private final Pattern buildingSingleNumberPattern = Pattern.compile("^\\d+");

  public Set<String> getNumbers(Collection<String> rawNumbers) {
    Set<String> exactNumbers = new HashSet<>();
    for (String rawNumber : rawNumbers) {
      if (rawNumber.contains("-")) {
        exactNumbers.addAll(getRangeNumbers(rawNumber));
      } else {
        getNumber(rawNumber).ifPresent(exactNumber -> exactNumbers.add(exactNumber));
      }
    }
    return exactNumbers;
  }

  public Optional<String> getNumber(String rawNumber) {
    if (rawNumber.contains("/")) {
      return Optional.of(rawNumber.replace("/", "-"));
    }
    Optional<String> cleanNumber = getCleanNumber(rawNumber);
    if (cleanNumber.isPresent()) {
      String number = cleanNumber.get();
      if (number.length() == rawNumber.length()) {
        return Optional.of(number);
      }
      return Optional.of(rawNumber.replaceFirst(number, number + "-"));
    }
    return cleanNumber;
  }

  private List<String> getRangeNumbers(String rawNumber) {
    List<String> exactNumbers = new ArrayList<>();
    try {
      String[] split = rawNumber.split("-");
      int end = Integer
          .parseInt(getCleanNumber(split[1]).orElseThrow(() -> new RuntimeException()));
      int start = Integer.parseInt(split[0]);
      for (; start < end; start++) {
        exactNumbers.add(start + "+");
      }
      exactNumbers.add(getNumber(split[1]).orElseThrow(() -> new RuntimeException()));
    } catch (RuntimeException ex) {
      logger.error("Error while range of building numbers creating.input:{" + rawNumber + "}", ex);
    }
    return exactNumbers;
  }

  private Optional<String> getCleanNumber(String number) {
    Matcher matcher = buildingSingleNumberPattern.matcher(number);
    String result = null;
    if (matcher.find()) {
      result = matcher.group();
    }
    return Optional.ofNullable(result);
  }
}
