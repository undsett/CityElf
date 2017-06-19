package com.cityelf.repository;

import com.cityelf.model.Address;

import org.springframework.data.repository.CrudRepository;

public interface AddressesRepository extends CrudRepository<Address, Long> {

  Address findByAddress(String address);

  Address findById(long id);

  Address save (Address address);


}
