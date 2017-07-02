package com.cityelf.repository;

import com.cityelf.model.Address;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AddressesRepository extends CrudRepository<Address, Long> {


  Address findByAddress(String address);

  Address findByaddressUa(String addressUa);

  Address findById(long id);

  Address save(Address address);


}
