package com.cityelf.repository;

import com.cityelf.model.Advertisement;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AdvertisementRepository extends CrudRepository<Advertisement, Long> {

  List<Advertisement> findByAddressId(long addressId);

}
