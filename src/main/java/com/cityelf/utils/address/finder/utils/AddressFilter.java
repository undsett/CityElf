package com.cityelf.utils.address.finder.utils;

import com.cityelf.model.Address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class AddressFilter {

  private final Pattern addressContainNumberPattern = Pattern.compile("(?<=\\s)\\d+(?=[^-]{0,1})");
  @Autowired
  private BuildingNumberExtender buildingNumberExtender;

  public List<Address> filterAddresses(List<Address> preSelectionAddresses,
      Collection<String> buildingNumbers, String streetName) {
    if (buildingNumbers.size() == 0) {
      return preSelectionAddresses;
    }
    Set<String> forecastBuildingNumbers = buildingNumberExtender.getNumbers(buildingNumbers);
    return preSelectionAddresses
        .stream()
        .filter(address -> {
          String number = null;
          Matcher matcher = addressContainNumberPattern.matcher(address.getAddress());
          while (matcher.find()) {
            number = matcher.group();
          }
          return forecastBuildingNumbers.contains(number);
        })
        .collect(Collectors.toList());
  }
}
