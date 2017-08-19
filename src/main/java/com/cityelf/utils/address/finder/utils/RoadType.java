package com.cityelf.utils.address.finder.utils;

import java.util.regex.Pattern;

public enum RoadType {
  STREET,
  LANE,
  AVENUE,
  BOULEVARD,
  DESCENT,
  LINE;

  private static Pattern streetPattern = Pattern
      .compile("^улица[\\s,]|\\sулица[\\s,]*|^ул[\\s\\.]|\\sул[\\s\\.]|"
          + "^вул[\\s\\.]|\\sвул[\\s\\.]|^вулыця[\\s,]|\\sвулыця[\\s,]*");
  private static Pattern lanePattern = Pattern
      .compile("^пер[\\s\\.]|\\sпер[\\s\\.]|^переулок[\\s,]|\\sпереулок[\\s,]*|"
          + "^провулок[\\s,]|\\sпровулок[\\s,]*");
  private static Pattern avenuePattern = Pattern
      .compile("^проспект[\\s,]|\\sпроспект[\\s,]*|^пр[\\s\\.]|\\sпр[\\s\\.]|"
          + "^просп[\\s\\.]|\\sпросп[\\s\\.]*");
  private static Pattern boulevardPattern = Pattern
      .compile("^бульвар[\\s,]|\\sбульвар[\\s,]*|^бул[\\s\\.]|\\sбул[\\s\\.]");
  private static Pattern descentPattern = Pattern
      .compile("^сп[\\s\\.]|\\sсп[\\s\\.]|^спуск[\\s,]|\\sспуск[\\s,]*|^узв.з[\\s,]|\\sузв.з[\\s,]");
  private static Pattern linePattern = Pattern.compile("[\\s-]линия");

  public static RoadType getRoadType(String streetName) {
    streetName = streetName.toLowerCase();
    if (lanePattern.matcher(streetName).find()) {
      return LANE;
    }
    if (avenuePattern.matcher(streetName).find()) {
      return AVENUE;
    }
    if (boulevardPattern.matcher(streetName).find()) {
      return BOULEVARD;
    }
    if (linePattern.matcher(streetName).find()) {
      return LINE;
    }
    if (descentPattern.matcher(streetName).find()) {
      return DESCENT;
    }
    return STREET;
  }
}
