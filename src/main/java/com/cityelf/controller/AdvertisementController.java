package com.cityelf.controller;

import com.cityelf.exceptions.AccessDeniedException;
import com.cityelf.exceptions.AddressNotPresentException;
import com.cityelf.exceptions.AdvertisementIncorrectException;
import com.cityelf.exceptions.AdvertisementNotFoundException;
import com.cityelf.model.Advertisement;
import com.cityelf.service.AdvertisementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/advertisements")
public class AdvertisementController {

  @Autowired
  private AdvertisementService advertisementService;

  @RequestMapping(value = "/getAll", method = RequestMethod.GET)
  public List<Advertisement> getAdvertisements(@RequestParam(name = "addressid") long addressId)
      throws AddressNotPresentException {
    return advertisementService.getAdvertisements(addressId);
  }

  @RequestMapping(value = "/getAdvertisement", method = RequestMethod.GET)
  public Advertisement getAdvertisementById(@RequestParam("id") long id)
      throws AdvertisementNotFoundException {
    return advertisementService.getAdvertisementById(id);
  }

  @RequestMapping(value = "/admin/addAdvertisement", method = RequestMethod.POST)
  public Advertisement addAdvertisement(@RequestBody Advertisement advertisement)
      throws AdvertisementIncorrectException, AddressNotPresentException, AccessDeniedException {
    return advertisementService.addAdvertisement(advertisement);
  }

  @RequestMapping(value = "/admin/updateAdvertisement", method = RequestMethod.PUT)
  public Advertisement updateAdvertisement(@RequestBody Advertisement advertisement)
      throws AdvertisementNotFoundException, AddressNotPresentException, AccessDeniedException {
    return advertisementService.updateAdvertisement(advertisement);
  }

  @RequestMapping(value = "/admin/deleteAdvertisement", method = RequestMethod.DELETE)
  public void deleteAdvertisement(@RequestParam("id") long id)
      throws AdvertisementNotFoundException, AccessDeniedException {
    advertisementService.deleteAdvertisement(id);
  }
}