package com.cityelf.model.water.parser;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
class StreetExtractor {

  private Pattern namePattern = Pattern.compile("[^,]+");

  public String getStreetName(String rawAdress) {
    Matcher matcher = namePattern.matcher(rawAdress);
    if (matcher.find()) {
      return matcher.group();
    } else {
      throw new RuntimeException("No street's found");
    }
  }
}
