package com.cityelf.repository;

import com.cityelf.model.Address;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AddressesRepository extends CrudRepository<Address, Long> {

  Address findByAddress(String address);

  Address findByAddressUa(String addressUa);

  Address findById(long id);

  Address save(Address address);

  @Query(value = "select * "
      + "from addresses "
      + "where match(street, street_ua) against(?1) and "
      + "match(street, street_ua) against(?1)=( "
      + "select max(match(street, street_ua) against(?1)) from addresses "
      + ")",
      nativeQuery = true)
  List<Address> findSimilarAddresses(String address);

  @Query(value = "select * "
      + "from addresses "
      + "where match(street, street_ua) against(?1) and "
      + "match(street, street_ua) against(?1)=( "
      + "select max(match(street, street_ua) against(?1)) from addresses "
      + ")and street  rlike ?2",
      nativeQuery = true)
  List<Address> findSimilarAddress(String address, String number);

}
