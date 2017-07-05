package com.cityelf.utils;

import com.cityelf.model.Address;
import com.cityelf.repository.AddressesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

  private static final String EXISTING_ADDRESS_FILENAME = "ExistingAddresses.txt";

  public void insertToDataBase(boolean checkInDb) throws Exception {
    Set<Address> addressList = loader.loadData();
    Set<Address> toWrite;
    if (checkInDb) {
      Set<Address>[] checked = this.checkInDb(addressList);
      toWrite = checked[0];
      this.saveExisting(checked[1]);
    } else {
      toWrite = addressList;
    }
    if (toWrite.size() > 0) {
      repository.save(toWrite);
    }
  }

  private Set<Address>[] checkInDb(Set<Address> addresses) {
    Set<Address>[] result;
    result = (addresses.size() > SIZE_THRESHOLD)
        ? checkForBigData(addresses) : checkForSmallData(addresses);
    return result;
  }

  private Set<Address>[] checkForBigData(Set<Address> addresses) {
    List<Address> allIn = StreamSupport.stream(repository.findAll().spliterator(), false)
        .collect(Collectors.toList());
    Set<Address> inDb = new LinkedHashSet<>();
    Set<Address> notInDb = new LinkedHashSet<>();
    for (Address address : addresses) {
      if (allIn.contains(address)) {
        inDb.add(address);
      } else {
        notInDb.add(address);
      }
    }
    return new Set[]{notInDb, inDb};
  }

  private Set<Address>[] checkForSmallData(Set<Address> addresses) {
    Set<Address> inDb = new LinkedHashSet<>();
    Set<Address> notInDb = new LinkedHashSet<>();
    for (Address address : addresses) {
      if ((repository.findByAddress(address.getAddress()) != null)
          || (repository.findByAddressUa(address.getAddressUa()) != null)) {
        inDb.add(address);
      } else {
        notInDb.add(address);
      }
    }
    return new Set[]{notInDb, inDb};
  }

  private void saveExisting(Set<Address> existing) throws IOException {
    PrintWriter writer = null;
    StringBuilder stringBuilder = new StringBuilder("This addresses are already in the DB\n");
    for (Address address : existing) {
      stringBuilder.append(String.format("%s;%s\n", address.getAddress(), address.getAddressUa()));
    }
    try {
      writer = new PrintWriter(new FileWriter(EXISTING_ADDRESS_FILENAME));
      writer.write(stringBuilder.toString().trim());
    } catch (IOException exc) {
      throw exc;
    } finally {
      if (writer != null) {
        writer.close();
      }
    }
  }
}
