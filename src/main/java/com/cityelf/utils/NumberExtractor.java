package com.cityelf.utils;

import com.cityelf.utils.address.finder.utils.BuildingNumberExtender;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class NumberExtractor {

  private Logger logger = LogManager.getLogger(getClass());
  @Autowired
  private BuildingNumberExtender buildingNumberExtender;
  private Pattern removeMinus = Pattern.compile("\\d+-");
  private final String streetReplacePattern = ".+?,";
  private final String stringRangePattern = "\\d+([\\s\\D]*)?-(\\s)?\\d+((\\/)?[^\\s,+]+)?";
  private Pattern rangeNumbersPattern = Pattern.compile(stringRangePattern);
  private Pattern standaloneNumbersPattern = Pattern
      .compile("\\d{1,3}([^,\\w\\/\\s]{1}|\\/[\\d\\S,]+){0,4}");
  private Pattern numberPattern = Pattern
      .compile("(?<=\\s)\\d{1,3}([^\\/\\s,]{1}|\\/[^\\s,]+){0,4}");

  private final String cleanRangePattern = "(?<=\\d{1,3})([^\\d]*)?(?=-\\d+(\\/.*)?)";

  private final String kostil = "\\d{4,}";

  private final Pattern kostil2 = Pattern.compile(streetReplacePattern, Pattern.CASE_INSENSITIVE);

  public Set<String> getNumbers(String rawAddress) {
    logger.debug("Input: " + rawAddress);
    Set<String> buildingNumberSet = new TreeSet<>();

    Matcher matcher = kostil2.matcher(rawAddress);
    if (matcher.find()) {
      String street = matcher.group();
      Optional<String> number = getNumber(street);
      if (number.isPresent()) {
        buildingNumberSet.add(number.get());
      }
    }

    String rawBuildingNumberString = rawAddress.replaceFirst(streetReplacePattern, "");
    logger.debug("After street replacing: " + rawBuildingNumberString);
    rawBuildingNumberString = getRange(rawBuildingNumberString, buildingNumberSet);
    logger.debug("After range removed:" + rawBuildingNumberString);
    logger.debug(buildingNumberSet);
    getSingle(rawBuildingNumberString, buildingNumberSet);
    logger.debug(buildingNumberSet);

    return buildingNumberExtender.getNumbers(buildingNumberSet);
  }

  public Optional<String> getNumber(String rawAddress) {
    rawAddress = removeMinus.matcher(rawAddress).replaceAll("");
    List<Optional<String>> numbers = new ArrayList<>();
    Matcher matcher = numberPattern.matcher(rawAddress);
    while (matcher.find()) {
      String group = matcher.group();
      if (!group.matches(kostil)) {
        numbers.add(buildingNumberExtender.getNumber(group));
      }
    }
    int size = numbers.size();
    return size == 0 ? Optional.empty() : numbers.get(size - 1);
  }

  private String getRange(String str, Set<String> set) {
    Matcher matcher = rangeNumbersPattern.matcher(str);
    while (matcher.find()) {
      set.add(matcher.group().replaceAll("\\s+", "").replaceAll(cleanRangePattern, ""));
    }
    return str.replaceAll(stringRangePattern, "");
  }

  private void getSingle(String str, Set<String> set) {
    Matcher matcher = standaloneNumbersPattern.matcher(str);
    while (matcher.find()) {
      set.add(matcher.group());
    }
  }
}