package com.cityelf.model.water.parser;

import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
class NumberExtractor {

  private String streetReplacePattern = ".+?,";
  private String stringRangePattern = "\\d+([\\s\\D]*)?-(\\s)?\\d+((\\/)?[^\\s,+]+)?";
  private Pattern rangeNumbersPattern = Pattern.compile(stringRangePattern);
  private Pattern standaloneNumbersPattern = Pattern.compile("\\d{1,3}([^,\\w\\/\\s]{1}|\\/[\\d\\S,]+?){0,4}");

  private String cleanRangePattern = "(?<=\\d{1,3})([^\\d]*)?(?=-\\d+(\\/.*)?)";


  public Set<String> getNumbers(String rawAddress) {
    Set<String> set = new TreeSet<>();
    String string = rawAddress.replaceFirst(streetReplacePattern, "");
    string = getRange(string, set);
    getSingle(string, set);

    return set;
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