package com.cityelf.utils;

import com.cityelf.model.Address;
import com.cityelf.repository.AddressesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
class AddressDbFinder {

  private final Pattern pattern = Pattern
      .compile("пер\\.|переулок|ул\\.|улица|пр\\.|проспект|тупик|бульвар|\\d");
  private final Pattern buildingSingleNumberPattern = Pattern.compile("^\\d+");
  private final Pattern addressContainNumberPattern = Pattern.compile("(?<=\\s)\\d+");
  @Autowired
  private AddressesRepository addressesRepository;

  public List<Address> getAddresses(String streetName, Collection<String> buildingNumbers) {
    String[] maskWords = getMaskWords(streetName);
    List<Address> preSelectionAddresses = addressesRepository.findAddressesByMask(maskWords);
    return filterAddresses(preSelectionAddresses, buildingNumbers);
  }

  private List<Address> filterAddresses(List<Address> preSelectionAddresses,
      Collection<String> numbers) {
    Set<String> forecastBuildingNumbers = getNumbers(numbers);
    return preSelectionAddresses
        .stream()
        .filter(address -> {
          Set<String> numbersFromAddresses = new HashSet<>();
          Matcher matcher = addressContainNumberPattern.matcher(address.getAddress());
          while (matcher.find()) {
            numbersFromAddresses.add(matcher.group());
          }
          return numbersFromAddresses
              .stream()
              .anyMatch(item -> forecastBuildingNumbers.contains(item));
        })
        .collect(Collectors.toList());
  }

  private Set<String> getNumbers(Collection<String> buildingNumbers) {
    Set<String> numbers = new HashSet<>();
    for (String buildingNumber : buildingNumbers) {
      if (buildingNumber.contains("-")) {
        numbers.addAll(rangeToSingle(buildingNumber));
      } else {
        getCleanNumber(buildingNumber)
            .ifPresent(number -> numbers.add(number));
      }
    }
    return numbers;
  }

  private List<String> rangeToSingle(String range) {
    List<String> numbers = new ArrayList<>();
    try {
      String[] split = range.split("-");
      String startRange = getCleanNumber(split[0]).orElseThrow(() -> new RuntimeException());
      String endRange = getCleanNumber(split[1]).orElseThrow(() -> new RuntimeException());
      int start = Integer.parseInt(startRange);
      int end = Integer.parseInt(endRange);
      for (int i = start; i <= end; i++) {
        numbers.add(String.valueOf(i));
      }
    } catch (RuntimeException ex) {
      System.err.println("Some error while range of building numbers creating");
      ex.printStackTrace();
    }
    return numbers;
  }

  private Optional<String> getCleanNumber(String number) {
    Matcher matcher = buildingSingleNumberPattern.matcher(number);
    String result = null;
    if (matcher.find()) {
      result = matcher.group();
    }
    return Optional.ofNullable(result);
  }

  private String[] getMaskWords(String streetName) {
    streetName = streetName.toLowerCase();
    String[] split = streetName.split("\\s");
    return Arrays.stream(split)
        .filter(item -> !pattern.matcher(item).find())
        .map(item -> "%" + item + "%")
        .collect(Collectors.toList()).toArray(new String[0]);
  }

}