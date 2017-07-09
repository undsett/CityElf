package com.cityelf.controller;

import com.cityelf.service.ShutdownsInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/allforecasts")
public class ShutdownsInfoController {

  @Autowired
  private ShutdownsInfoService shutdownsInfoService;

  @RequestMapping(value = "/get", method = RequestMethod.GET)
  public Map<String, Object> getAllForecasts(@RequestParam(name = "start") String startTime,
      @RequestParam(name = "address") String address) {
    return shutdownsInfoService.getAllForecasts(LocalDateTime.parse(startTime), address);
  }
}
