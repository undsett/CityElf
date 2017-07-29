package com.cityelf.utils.address.finder.utils;

import com.cityelf.model.Address;
import com.cityelf.utils.NumberExtractor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AddressFilter {

  @Autowired
  private NumberExtractor numberExtractor;

  public List<Address> filterAddresses(List<Address> preSelectionAddresses,
      Collection<String> buildingNumbers, String streetName) {
    return buildingNumbers.isEmpty()
        ? preSelectionAddresses
        : preSelectionAddresses
            .stream()
            .filter(address -> {
              String streetNameDb = address.getAddress();
              Optional<String> number = numberExtractor.getNumber(streetNameDb);
              if (number.isPresent()) {
                return (buildingNumbers.contains(number.get()) || buildingNumbers
                    .contains(number.get().split("-")[0] + "+"))
                    && RoadType.getRoadType(streetNameDb) == RoadType.getRoadType(streetName);
              }
              return false;
            })
            .collect(Collectors.toList());
  }
}
