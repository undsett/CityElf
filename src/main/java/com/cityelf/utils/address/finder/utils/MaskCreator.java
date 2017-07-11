package com.cityelf.utils.address.finder.utils;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class MaskCreator {

  private final Pattern typeStreetPattern = Pattern
      .compile("пер\\.|переулок|ул\\.|улица|пр\\.|проспект|тупик|бульвар|сп.|спуск|\\d+|.{1}");
  private final Pattern regionPattern = Pattern.compile("малиновский|мал|приморский");

  public List<String> getMaskWords(String streetName) {
    streetName = streetName.toLowerCase();
    String[] split = streetName.split("[\\s-]");
    return Arrays.stream(split)
        .filter(item -> !(typeStreetPattern.matcher(item).matches()
            || item.isEmpty()
            || regionPattern.matcher(item).matches())
        )
        .map(item -> "%" + item + "%")
        .collect(Collectors.toList());
  }
}
