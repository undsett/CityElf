package com.cityelf.repository;

import com.cityelf.model.Address;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AddressesRepository extends CrudRepository<Address, Long> {

  Address findByAddress(String address);

  Address findById(long id);

  Address save(Address address);

  List<Address> findAllByAddressLike(String part);

  List<Address> findAllByAddressLikeAndAddressLike(String part1, String part2);

  List<Address> findAllByAddressLikeAndAddressLikeAndAddressLike(String part1, String part2,
      String part3);

  List<Address> findAllByAddressLikeAndAddressLikeAndAddressLikeAndAddressLike(String part1,
      String part2, String part3, String part4);

  default List<Address> findAddressesByMask(String... parts) {
    int countParams = parts.length;
    switch (countParams) {
      case 1:
        return findAllByAddressLike(parts[0]);
      case 2:
        return findAllByAddressLikeAndAddressLike(parts[0], parts[1]);
      case 3:
        return findAllByAddressLikeAndAddressLikeAndAddressLike(parts[0], parts[1], parts[2]);
      default:
        return findAllByAddressLikeAndAddressLikeAndAddressLikeAndAddressLike(parts[0], parts[1],
            parts[2], parts[3]);
    }
  }

}
