package com.cityelf.utils;

import com.cityelf.model.Address;
import com.cityelf.repository.AddressesRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class AddressesFiller {

  @Autowired
  private AddressesLoader loader;

  @Autowired
  private AddressesRepository repository;

  private static final int SIZE_THRESHOLD = 100;

  private static final Logger logger = LoggerFactory.getLogger(AddressesFiller.class);

  public void insertToDataBase(boolean checkInDb) throws Exception {
    Set<Address> addressList = loader.loadData();
    Set<Address> toWrite;
    if (checkInDb) {
      toWrite = this.checkInDb(addressList);
    } else {
      toWrite = addressList;
    }
    if (toWrite.size() > 0) {
      repository.save(toWrite);
    }
  }

  private Set<Address> checkInDb(Set<Address> addresses) {
    List<Address> allIn = loadInDb(addresses);
    List<String> names = allIn.stream().map(Address::getAddress).collect(Collectors.toList());
    List<String> namesUa = allIn.stream().map(Address::getAddressUa).collect(Collectors.toList());
    List<Address> existed = addresses.stream()
        .filter(
            x -> names.contains(x.getAddress())
                || namesUa.contains(x.getAddressUa())
                || x.getAddress().matches("(.*)\\dё"))
        .collect(Collectors.toList());
    addresses.removeAll(existed);
    existed.forEach(this::logExisting);

    return new LinkedHashSet<>(addresses);
  }

  private List<Address> loadInDb(Set<Address> checkedData) {
    List<Address> allIn;
    if (checkedData.size() > SIZE_THRESHOLD) {
      allIn = StreamSupport.stream(repository.findAll().spliterator(), false)
          .collect(Collectors.toList());
    } else {
      allIn = new ArrayList<>(repository.findByAddressInOrAddressUaIn(
          checkedData.stream().map(Address::getAddress).collect(Collectors.toList()),
          checkedData.stream().map(Address::getAddressUa).collect(Collectors.toList())));
    }
    return allIn;
  }

  private void logExisting(Address existing) {
    logger.info(String.format("Address '%s' - '%s' already exists in the DB.\n\tIts ID = %s",
        existing.getAddress(), existing.getAddressUa(), existing.getId()));
  }
}
