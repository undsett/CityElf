package com.cityelf.controller;

import com.cityelf.exceptions.AddressException;
import com.cityelf.service.ShutdownsInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/services/allforecasts")
public class ShutdownsInfoController {

  @Autowired
  private ShutdownsInfoService shutdownsInfoService;

  @RequestMapping(value = "/get", method = RequestMethod.GET)
  public List<Map<String, Object>> getAllForecasts(@RequestParam(name = "address") String address)
      throws AddressException {
    return shutdownsInfoService.getAllForecasts(address);
  }
}
