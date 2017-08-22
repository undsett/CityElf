package com.cityelf;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cityelf.controller.ShutdownsInfoController;
import com.cityelf.model.Address;
import com.cityelf.model.WaterForecast;
import com.cityelf.service.ShutdownsInfoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ShutdownsInfoController.class, secure = false)
public class ShutdownInfoControllerTest {

  List<Map<String, Object>> list;

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
    list = new ArrayList<>();
    Map<String, Object> map = new HashMap<>();
    waterForecast = new WaterForecast();
    waterForecast.setAddress(address);
    waterForecast.setStart(LocalDateTime.now());
    map.put("Water", waterForecast);
    list.add(map);
  }

  @Test
  public void getAllForecastsByTimeAndAddressShouldReturnHttpStatusOk200() throws Exception {
    when(shutdownsInfoService.getAllForecasts(any(String.class)))
        .thenReturn(list);

    mockMvc.perform(get("/services/allforecasts/get")
        .param("start", waterForecast.getStart().toString())
        .param("address", waterForecast.getAddress().getAddress()))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void getAllForecastsByTimeAndAddressShouldReturnJson() throws Exception {
    when(shutdownsInfoService.getAllForecasts(any(String.class)))
        .thenReturn(list);

    List<Map<String, Object>> forecasts = new ArrayList<>();
    Map<String, Object> expectedMap = new HashMap<>();
    expectedMap.put("Water", waterForecast);
    forecasts.add(expectedMap);

    mockMvc.perform(get("/services/allforecasts/get")
        .param("start", waterForecast.getStart().toString())
        .param("address", waterForecast.getAddress().getAddress()))
        .andDo(print())
        .andExpect(content().string(objectToJson(forecasts)));
  }
}
