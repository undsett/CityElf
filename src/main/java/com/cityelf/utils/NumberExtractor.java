package com.cityelf.utils;

import com.cityelf.utils.address.finder.utils.BuildingNumberExtender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class NumberExtractor {

  @Autowired
  private BuildingNumberExtender buildingNumberExtender;
  private Pattern removeMinus = Pattern.compile("\\d+-");
  private final String streetReplacePattern = ".+?,";
  private final String stringRangePattern = "\\d+([\\s\\D]*)?-(\\s)?\\d+((\\/)?[^\\s,+]+)?";
  private Pattern rangeNumbersPattern = Pattern.compile(stringRangePattern);
  private Pattern standaloneNumbersPattern = Pattern
      .compile("\\d{1,3}([^,\\w\\/\\s]{1}|\\/[\\d\\S,]+?){0,4}");

  private final String cleanRangePattern = "(?<=\\d{1,3})([^\\d]*)?(?=-\\d+(\\/.*)?)";


  public Set<String> getNumbers(String rawAddress) {
    Set<String> buildingNumberSet = new TreeSet<>();
    String rawBuildingNumberString = rawAddress.replaceFirst(streetReplacePattern, "");
    rawBuildingNumberString = getRange(rawBuildingNumberString, buildingNumberSet);
    getSingle(rawBuildingNumberString, buildingNumberSet);

    return buildingNumberExtender.getNumbers(buildingNumberSet);
  }

  public Optional<String> getNumber(String rawAddress) {
    rawAddress = removeMinus.matcher(rawAddress).replaceAll("");
    Matcher matcher = standaloneNumbersPattern.matcher(rawAddress);
    if (matcher.find()) {
      return buildingNumberExtender.getNumber(matcher.group());
    }
    return Optional.empty();
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