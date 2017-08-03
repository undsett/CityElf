package com.cityelf.repository;

import com.cityelf.model.User;
import com.cityelf.model.UserAddresses;

import org.springframework.data.repository.CrudRepository;


public interface UserAddressesRepository extends CrudRepository<UserAddresses, Long> {

  UserAddresses findByUserId(Long userId);

  int findByAddressId(String addressId);

  UserAddresses save(UserAddresses userAddresses);

  void deleteUserAddressesByUserId(Long userId);

}
