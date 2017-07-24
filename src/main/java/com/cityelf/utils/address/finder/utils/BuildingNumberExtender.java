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

  public Set<String> getNumbers(Collection<String> buildingNumbers) {
    Set<String> numbers = new HashSet<>();
    for (String buildingNumber : buildingNumbers) {
      if (buildingNumber.contains("-")) {
        numbers.addAll(rangeToSingle(buildingNumber));
      } else {
        getCleanNumber(buildingNumber)
            .ifPresent(number -> numbers.add(number));
      }
    }
    return numbers;
  }

  public Optional<String> getNumber(String buildingNumber) {
    return getCleanNumber(buildingNumber);
  }

  private List<String> rangeToSingle(String range) {
    List<String> numbers = new ArrayList<>();
    try {
      String[] split = range.split("-");
      String startRange = getCleanNumber(split[0]).orElseThrow(() -> new RuntimeException());
      String endRange = getCleanNumber(split[1]).orElseThrow(() -> new RuntimeException());
      int start = Integer.parseInt(startRange);
      int end = Integer.parseInt(endRange);
      for (int i = start; i <= end; i++) {
        numbers.add(String.valueOf(i));
      }
    } catch (RuntimeException ex) {
      logger.error("Error while range of building numbers creating.input:{" + range + "}");
      ex.printStackTrace();
    }
    return numbers;
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
