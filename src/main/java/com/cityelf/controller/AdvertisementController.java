package com.cityelf.controller;

import com.cityelf.model.Advertisement;
import com.cityelf.service.AdvertisementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/alladvertisements")
public class AdvertisementController {

  @Autowired
  private AdvertisementService advertisementService;

  @RequestMapping(value = "/get", method = RequestMethod.GET)
  public List<Advertisement> getAdvertisements(@RequestParam(name = "addressid") long addressId) {
    return advertisementService.getAdvertisements(addressId);
  }

}
