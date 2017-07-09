package com.cityelf.utils.address.finder.utils;

import com.cityelf.model.Address;
import com.cityelf.repository.AddressesRepository;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class AddressDbFinder {

  private final Logger logger = LogManager.getLogger(getClass());
  @Autowired
  private AddressFilter addressFilter;
  @Autowired
  private StringSplitter stringSplitter;
  @Autowired
  private AddressesRepository addressesRepository;

  public List<Address> getAddresses(String streetName, Collection<String> buildingNumbers) {
    streetName = streetName.toLowerCase();
    List<String> maskWords = stringSplitter.getMaskWords(streetName);
    List<Address> preSelectionAddresses = addressesRepository
        .findAddressesByMask(maskWords.toArray(new String[0]));
    List<Address> addresses = addressFilter
        .filterAddresses(preSelectionAddresses, buildingNumbers, streetName);

    logger.trace("StreetName:" + streetName + "\nBuildings: " + buildingNumbers);
    logger.trace("Keywords for lookup from database:" + maskWords);
    logger.trace("Total count addresses found from DB by mask:" + preSelectionAddresses.size());
    logger
        .trace("Total count addresses filtered from preselection by mask:" + addresses.size());
    return addresses;
  }
}