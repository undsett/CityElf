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

import com.cityelf.controller.ElectricityForecastController;
import com.cityelf.exceptions.ForecastNotFoundException;
import com.cityelf.model.Address;
import com.cityelf.model.ElectricityForecast;
import com.cityelf.service.ElectricityForecastService;
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
@WebMvcTest(value = ElectricityForecastController.class, secure = false)
public class ElectricityForecastControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ElectricityForecastService electricityForecastService;
  private ElectricityForecast electricityForecast = new ElectricityForecast();
  private Address address = new Address();

  public String objectToJson(Object o) throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    return mapper.writeValueAsString(o);
  }

  @Before
  public void setUp() {
    electricityForecast.setAddress(address);
    electricityForecast.setStart(LocalDateTime.now());
  }

  @Test
  public void getAll() throws Exception {
    when(electricityForecastService.getAll()).thenReturn(Arrays.asList(electricityForecast));

    mockMvc.perform(get("/services/electricityforecast/all"))
        .andDo(print())
        .andExpect(content().string(objectToJson(Arrays.asList(electricityForecast))));
  }

  @Test
  public void getElectricityForecastShouldReturnHttpStatusNotFound404() throws Exception {
    when(electricityForecastService.getForecast(anyInt()))
        .thenThrow(ForecastNotFoundException.class);

    mockMvc.perform(get("/services/electricityforecast/1"))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  public void getElectricityForecastShouldReturnHttpStatusOk200() throws Exception {
    when(electricityForecastService.getForecast(anyInt()))
        .thenReturn(electricityForecast);

    mockMvc.perform(get("/services/electricityforecast/2"))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void getElectricityForecastShouldReturnJson() throws Exception {
    when(electricityForecastService.getForecast(anyInt()))
        .thenReturn(electricityForecast);

    mockMvc.perform(get("/services/electricityforecast/2"))
        .andDo(print())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().string(objectToJson(electricityForecast)));
  }
//TODO
//  @Test
//  public void getElectricityForecastByTimeAndAddressShouldReturnHttpStatusOk200() throws Exception {
//    when(electricityForecastService.getForecast(any(LocalDateTime.class), any(String.class)))
//        .thenReturn(electricityForecast);
//
//    mockMvc.perform(get("/electricityforecast/get")
//        .param("start", electricityForecast.getStart().toString())
//        .param("address", electricityForecast.getAddress().getAddress()))
//        .andDo(print())
//        .andExpect(status().isOk());
//  }
//
//  @Test
//  public void getElectricityForecastByTimeAndAddressShouldReturnJson() throws Exception {
//    when(electricityForecastService.getForecast(any(LocalDateTime.class), any(String.class)))
//        .thenReturn(electricityForecast);
//
//    mockMvc.perform(get("/electricityforecast/get")
//        .param("start", electricityForecast.getStart().toString())
//        .param("address", electricityForecast.getAddress().getAddress()))
//        .andDo(print())
//        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
//        .andExpect(content().string(objectToJson(electricityForecast)));
//  }

  @Test
  public void getElectricityForecastsByStartTimeShouldReturnHttpStatusOk200() throws Exception {
    when(electricityForecastService.getForecastsByTime(any(LocalDateTime.class)))
        .thenReturn(Arrays.asList(electricityForecast));

    mockMvc.perform(get("/services/electricityforecast/getbystart")
        .param("start", electricityForecast.getStart().toString()))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void getElectricityForecastsByStartTimeShouldReturnJson() throws Exception {
    when(electricityForecastService.getForecastsByTime(any(LocalDateTime.class)))
        .thenReturn(Arrays.asList(electricityForecast));

    mockMvc.perform(get("/services/electricityforecast/getbystart")
        .param("start", electricityForecast.getStart().toString()))
        .andDo(print())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().string(objectToJson(Arrays.asList(electricityForecast))));
  }

  @Test
  public void getCurrentElectricityForecastsShouldReturnHttpStatusOk200() throws Exception {
    when(electricityForecastService.getCurrentElectricityForecasts(any(LocalDateTime.class)))
        .thenReturn(Arrays.asList(electricityForecast));

    mockMvc.perform(get("/services/electricityforecast/getcurrent"))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void getCurrentElectricityForecastsShouldReturnJson() throws Exception {
    electricityForecast.setEstimatedStop(LocalDateTime.of(3000, 1, 1, 0, 0));
    when(electricityForecastService.getCurrentElectricityForecasts(any(LocalDateTime.class)))
        .thenReturn(Arrays.asList(electricityForecast));

    mockMvc.perform(get("/services/electricityforecast/getcurrent"))
        .andDo(print())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().string(objectToJson(Arrays.asList(electricityForecast))));
  }

  @Test
  public void getAddressesByTimeShouldReturnHttpStatusOk200() throws Exception {
    when(electricityForecastService.getAddressesByTime(any(LocalDateTime.class)))
        .thenReturn(new HashSet(Arrays.asList(address)));

    mockMvc.perform(get("/services/electricityforecast/getaddressesbytime")
        .param("start", electricityForecast.getStart().toString()))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void getAddressesByTimeShouldReturnJson() throws Exception {
    when(electricityForecastService.getAddressesByTime(any(LocalDateTime.class)))
        .thenReturn(new HashSet(Arrays.asList(address)));

    mockMvc.perform(get("/services/electricityforecast/getaddressesbytime")
        .param("start", electricityForecast.getStart().toString()))
        .andDo(print())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().string(objectToJson(new HashSet(Arrays.asList(address)))));
  }

  @Test
  public void addNewElectricityForecastShouldReturnHttpStatusOk200() throws Exception {
    doNothing().when(electricityForecastService).addNewElectricityForecast(any());

    mockMvc.perform(post("/services/electricityforecast/addnew")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(objectToJson(electricityForecast))
    )
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void updateElectricityForecastShouldReturnHttpStatusOk200() throws Exception {
    doNothing().when(electricityForecastService).updateElectricityForecast(any());

    mockMvc.perform(
        put("/services/electricityforecast/update")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(objectToJson(electricityForecast))
    )
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void updateElectricityForecastShouldReturnHttpStatusNotFound404() throws Exception {
    doThrow(ForecastNotFoundException.class).when(electricityForecastService)
        .updateElectricityForecast(any());

    mockMvc.perform(
        put("/services/electricityforecast/update")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(objectToJson(electricityForecast))
    )
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  public void deleteElectricityForecastShouldReturnHttpStatusOk200() throws Exception {
    mockMvc.perform(
        delete("/services/electricityforecast/delete")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(objectToJson(electricityForecast))
    )
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void deleteElectricityForecastShouldReturnHttpStatusNotFound404() throws Exception {
    doThrow(ForecastNotFoundException.class).when(electricityForecastService)
        .deleteElectricityForecast(any());

    mockMvc.perform(
        delete("/services/electricityforecast/delete")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(objectToJson(electricityForecast))
    )
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  public void deleteElectricityForecastsByTimeShouldReturnHttpStatusOk200() throws Exception {
    mockMvc.perform(
        delete("/services/electricityforecast/deleteallbytime")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(objectToJson(LocalDateTime.now()))
    )
        .andDo(print())
        .andExpect(status().isOk());
  }
}
