package com.cityelf;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cityelf.controller.WaterForecastController;
import com.cityelf.exceptions.ForecastNotFoundException;
import com.cityelf.model.Address;
import com.cityelf.model.WaterForecast;
import com.cityelf.service.WaterForecastService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;

@RunWith(SpringRunner.class)
@WebMvcTest(value = WaterForecastController.class, secure = false)
public class WaterForecastControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private WaterForecastService waterForecastService;
  private WaterForecast waterForecast = new WaterForecast();
  private Address address = new Address();

  public String objectToJson(Object o) throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    return mapper.writeValueAsString(o);
  }

  @Before
  public void setUp() {
    waterForecast.setAddress(address);
    waterForecast.setStart(LocalDateTime.now());
  }

  @Test
  public void getAll() throws Exception {
    when(waterForecastService.getAll()).thenReturn(Arrays.asList(waterForecast));

    mockMvc.perform(get("/services/waterforecast/all"))
        .andDo(print())
        .andExpect(content().string(objectToJson(Arrays.asList(waterForecast))));
  }

  @Test
  public void getWaterForecastShouldReturnHttpStatusNotFound404() throws Exception {
    when(waterForecastService.getForecast(anyInt()))
        .thenThrow(ForecastNotFoundException.class);

    mockMvc.perform(get("/services/waterforecast/1"))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  public void getWaterForecastShouldReturnHttpStatusOk200() throws Exception {
    when(waterForecastService.getForecast(anyInt()))
        .thenReturn(waterForecast);

    mockMvc.perform(get("/services/waterforecast/2"))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void getWaterForecastShouldReturnJson() throws Exception {
    when(waterForecastService.getForecast(anyInt()))
        .thenReturn(waterForecast);

    mockMvc.perform(get("/services/waterforecast/2"))
        .andDo(print())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().string(objectToJson(waterForecast)));
  }

  @Test
  public void getWaterForecastByTimeAndAddressShouldReturnHttpStatusOk200() throws Exception {
    when(waterForecastService.getForecast(any(LocalDateTime.class), any(String.class)))
        .thenReturn(waterForecast);

    mockMvc.perform(get("/services/waterforecast/get")
        .param("start", waterForecast.getStart().toString())
        .param("address", waterForecast.getAddress().getAddress()))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void getWaterForecastByTimeAndAddressShouldReturnJson() throws Exception {
    when(waterForecastService.getForecast(any(LocalDateTime.class), any(String.class)))
        .thenReturn(waterForecast);

    mockMvc.perform(get("/services/waterforecast/get")
        .param("start", waterForecast.getStart().toString())
        .param("address", waterForecast.getAddress().getAddress()))
        .andDo(print())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().string(objectToJson(waterForecast)));
  }

  @Test
  public void getWaterForecastsByStartTimeShouldReturnHttpStatusOk200() throws Exception {
    when(waterForecastService.getForecastsByTime(any(LocalDateTime.class)))
        .thenReturn(Arrays.asList(waterForecast));

    mockMvc.perform(get("/services/waterforecast/getbystart")
        .param("start", waterForecast.getStart().toString()))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void getWaterForecastsByStartTimeShouldReturnJson() throws Exception {
    when(waterForecastService.getForecastsByTime(any(LocalDateTime.class)))
        .thenReturn(Arrays.asList(waterForecast));

    mockMvc.perform(get("/services/waterforecast/getbystart")
        .param("start", waterForecast.getStart().toString()))
        .andDo(print())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().string(objectToJson(Arrays.asList(waterForecast))));
  }

  @Test
  public void getCurrentWaterForecastsShouldReturnHttpStatusOk200() throws Exception {
    when(waterForecastService.getCurrentWaterForecasts(any(LocalDateTime.class)))
        .thenReturn(Arrays.asList(waterForecast));

    mockMvc.perform(get("/services/waterforecast/getcurrent"))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void getCurrentWaterForecastsShouldReturnJson() throws Exception {
    waterForecast.setEstimatedStop(LocalDateTime.of(3000, 1, 1, 0, 0));
    when(waterForecastService.getCurrentWaterForecasts(any(LocalDateTime.class)))
        .thenReturn(Arrays.asList(waterForecast));

    mockMvc.perform(get("/services/waterforecast/getcurrent"))
        .andDo(print())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().string(objectToJson(Arrays.asList(waterForecast))));
  }

  @Test
  public void getAddressesByTimeShouldReturnHttpStatusOk200() throws Exception {
    when(waterForecastService.getAddressesByTime(any(LocalDateTime.class)))
        .thenReturn(new HashSet(Arrays.asList(address)));

    mockMvc.perform(get("/services/waterforecast/getaddressesbytime")
        .param("start", waterForecast.getStart().toString()))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void getAddressesByTimeShouldReturnJson() throws Exception {
    when(waterForecastService.getAddressesByTime(any(LocalDateTime.class)))
        .thenReturn(new HashSet(Arrays.asList(address)));

    mockMvc.perform(get("/services/waterforecast/getaddressesbytime")
        .param("start", waterForecast.getStart().toString()))
        .andDo(print())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().string(objectToJson(new HashSet(Arrays.asList(address)))));
  }

  @Test
  public void addNewWaterForecastShouldReturnHttpStatusOk200() throws Exception {
    doNothing().when(waterForecastService).addNewWaterForecast(any());

    mockMvc.perform(post("/services/waterforecast/addnew")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(objectToJson(waterForecast))
    )
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void updateWaterForecastShouldReturnHttpStatusOk200() throws Exception {
    doNothing().when(waterForecastService).updateWaterForecast(any());

    mockMvc.perform(
        put("/services/waterforecast/update")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(objectToJson(waterForecast))
    )
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void updateWaterForecastShouldReturnHttpStatusNotFound404() throws Exception {
    doThrow(ForecastNotFoundException.class).when(waterForecastService).updateWaterForecast(any());

    mockMvc.perform(
        put("/services/waterforecast/update")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(objectToJson(waterForecast))
    )
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  public void deleteWaterForecastShouldReturnHttpStatusOk200() throws Exception {
    mockMvc.perform(
        delete("/services/waterforecast/delete")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(objectToJson(waterForecast))
    )
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void deleteWaterForecastShouldReturnHttpStatusNotFound404() throws Exception {
    doThrow(ForecastNotFoundException.class).when(waterForecastService).deleteWaterForecast(any());

    mockMvc.perform(
        delete("/services/waterforecast/delete")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(objectToJson(waterForecast))
    )
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  public void deleteWaterForecastsByTimeShouldReturnHttpStatusOk200() throws Exception {
    mockMvc.perform(
        delete("/services/waterforecast/deleteallbytime")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(objectToJson(LocalDateTime.now()))
    )
        .andDo(print())
        .andExpect(status().isOk());
  }
}
