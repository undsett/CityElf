package com.cityelf;


import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cityelf.controller.ShutdownsInfoController;
import com.cityelf.model.Address;
import com.cityelf.model.WaterForecast;
import com.cityelf.service.ShutdownsInfoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ShutdownsInfoController.class, secure = false)
public class ShutdownInfoControllerTest {

  Map<String, Object> map;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ShutdownsInfoService shutdownsInfoService;

  private WaterForecast waterForecast;
  private Address address = new Address();


  public String objectToJson(Object o) throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    return mapper.writeValueAsString(o);
  }

  @Before
  public void SetUp() {
    map = new HashMap<>();
    waterForecast = new WaterForecast();
    waterForecast.setAddress(address);
    waterForecast.setStart(LocalDateTime.now());
    map.put("Water", waterForecast);
  }

  @Test
  public void getAllForecastsByTimeAndAddressShouldReturnHttpStatusOk200() throws Exception {
    when(shutdownsInfoService.getAllForecasts(any(LocalDateTime.class), any(String.class)))
        .thenReturn(map);

    mockMvc.perform(get("/allforecasts/get")
        .param("start", waterForecast.getStart().toString())
        .param("address", waterForecast.getAddress().getAddress()))
        .andDo(print())
        .andExpect(status().isOk());
  }

//  @Test
//  public void getAllForecastsByTimeAndAddressShouldReturnJson() throws Exception {
//    when(shutdownsInfoService.getAllForecasts(any(LocalDateTime.class), any(String.class)))
//        .thenReturn(map);
//
//    Map<String, Object> expectedMap = new HashMap<>();
//    expectedMap.put("Water", waterForecast);
//
//    mockMvc.perform(get("/allforecast/get")
//        //.contentType(MediaType.APPLICATION_FORM_URLENCODED)
//        .param("start", waterForecast.getStart().toString())
//        .param("address", waterForecast.getAddress().getAddress()))
//        .andDo(print())
//        //.andExpect(content().contentType(MediaType.APPLICATION_FORM_URLENCODED))
//        .andExpect(content().string(objectToJson(expectedMap)));
//  }

}
