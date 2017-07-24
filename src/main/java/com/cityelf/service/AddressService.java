package com.cityelf.service;

import com.cityelf.exceptions.AddressException;
import com.cityelf.exceptions.NotFoundNumberException;
import com.cityelf.model.Address;
import com.cityelf.repository.AddressesRepository;
import com.cityelf.utils.NumberExtractor;
import com.cityelf.utils.address.finder.utils.AddressFilter;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class AddressService {

  private final Logger logger = LogManager.getLogger(getClass());
  private final String removePatternString = "одесса|одеса|одесская\\sобл[^\\s]*|одеська\\sобл[^\\s]*|";
  private Pattern removePattern = Pattern.compile(removePatternString);
  @Autowired
  private AddressesRepository addressesRepository;
  @Autowired
  private AddressFilter addressFilter;
  @Autowired
  private NumberExtractor numberExtractor;

  public List<Address> getAddresses(String streetName, Collection<String> numbers) {
    logger.trace("***\nStreetName: " + streetName + "Buildings: " + numbers);

    List<Address> preSelectionAddresses = addressesRepository.findSimilarAddresses(streetName);
    logger.trace("Count of addresses found while DB preselection: " + preSelectionAddresses.size());

    List<Address> addresses = addressFilter
        .filterAddresses(preSelectionAddresses, numbers, streetName);
    logger.trace("Total count addresses filtered from preselection: " + addresses.size());
    return addresses;
  }

  public Optional<Address> getAddress(String address) throws AddressException {
    logger.debug("***Custom address:" + address);
    address = removePattern.matcher(address.toLowerCase()).replaceAll("");
    logger.debug("Transformed custom address:" + address);

    String number = numberExtractor.getNumber(address)
        .orElseThrow(() -> new NotFoundNumberException());
    logger.debug("Building number extracted: " + number.toString());

    List<Address> preSelectionAddresses = addressesRepository.findSimilarAddress(address, number);
    List<Address> addresses = addressFilter
        .filterAddresses(preSelectionAddresses, Arrays.asList(number), address);
    logger.debug("Address found: " + addresses);
    return addresses.size() == 0 ? Optional.empty() : Optional.of(addresses.get(0));
  }
}
