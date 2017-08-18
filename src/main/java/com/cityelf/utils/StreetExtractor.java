package com.cityelf.utils;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class StreetExtractor {

  private Pattern namePattern = Pattern.compile("[^,]+");

  public String getStreetName(String rawAddress) {
    Matcher matcher = namePattern.matcher(rawAddress);
    if (matcher.find()) {
      return matcher.group();
    }
    throw new RuntimeException("No street's found");
  }
}
