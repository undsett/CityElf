package com.cityelf.utils.address.finder.utils;

import com.cityelf.model.Address;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class AddressFilter {

  private final Pattern addressContainNumberPattern = Pattern.compile("(?<=\\s)\\d+(?=[^-]{0,1})");

  public List<Address> filterAddresses(List<Address> preSelectionAddresses,
      Collection<String> buildingNumbers, String streetName) {
    if (buildingNumbers.size() == 0) {
      return preSelectionAddresses;
    }
    return preSelectionAddresses
        .stream()
        .filter(address -> {
          String number = null;
          Matcher matcher = addressContainNumberPattern.matcher(address.getAddress());
          while (matcher.find()) {
            number = matcher.group();
          }
          return buildingNumbers.contains(number);
        })
        .collect(Collectors.toList());
  }
}
