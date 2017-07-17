package com.cityelf.service;


import com.cityelf.model.Advertisement;
import com.cityelf.repository.AdvertisementRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdvertisementService {

  @Autowired
  private AdvertisementRepository advertisementRepository;

  public List<Advertisement> getAdvertisements(long addressId) {
    return advertisementRepository.findByAddressId(addressId);
  }

}
