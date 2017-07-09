package com.cityelf.utils;

import com.cityelf.model.Address;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.LinkedHashSet;
import java.util.Set;

@Component
class AddressesLoader {

  Set<Address> loadData() throws Exception {
    Set<Address> addressSet = new LinkedHashSet<>();
    String splitter = ";";
    ClassLoader classLoader = getClass().getClassLoader();
    File inputFile = new File(classLoader.getResource("Odessa_all.txt").getFile());
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(
        new FileInputStream(inputFile), Charset.forName("UTF-8")))) {
      String line;
      if (((line = reader.readLine()) != null) && (!lineContainsHeader(line))) {
        addressSet.add(new AddressEntity(line, splitter).toAddress());
      }
      while ((line = reader.readLine()) != null) {
        addressSet.add(new AddressEntity(line, splitter).toAddress());
      }
    } catch (Exception exc) {
      throw exc;
    }
    return addressSet;
  }

  private boolean lineContainsHeader(String line) {
    return (line.contains("id") || line.contains("street_name")
        || line.contains("street_name_ua") || line.contains("number"));
  }

  private class AddressEntity {

    private String streetName;
    private String streetNameUa;
    private String number;

    AddressEntity(String rawData, String splitter) throws Exception {
      String[] inputData = rawData.split(splitter);
      if (inputData.length != 4) {
        throw new IllegalArgumentException("Unsupported file data strings");
      }
      this.streetName = inputData[1];
      this.streetNameUa = inputData[2];
      this.number = inputData[3];
    }

    private Address toAddress() {
      String address = String.format("%s, %s",
          this.streetName.trim(), this.number.trim().toLowerCase());
      String addressUa = String.format("%s, %s",
          this.streetNameUa.trim(), this.number.trim().toLowerCase());
      return new Address(address, addressUa);
    }
  }
}
