package com.cityelf;

import com.cityelf.controller.GasForecastController;
import com.cityelf.exceptions.ForecastNotFoundException;
import com.cityelf.model.Address;
import com.cityelf.model.GasForecast;
import com.cityelf.service.GasForecastService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

@RunWith(SpringRunner.class)
@WebMvcTest(value = GasForecastController.class, secure = false)
public class GasForecastControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GasForecastService gasForecastService;
    private GasForecast gasForecast = new GasForecast();
    private Address address = new Address();

    public String objectToJson(Object o) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper.writeValueAsString(o);
    }

    @Test
    public void getAll() throws Exception {
        when(gasForecastService.getAll()).thenReturn(Arrays.asList(gasForecast));

        mockMvc.perform(get("/gasforecast/all"))
                .andDo(print())
                .andExpect(content().string(objectToJson(Arrays.asList(gasForecast))));
    }

    @Test
    public void getGasForecastShouldReturnHttpStatusNotFound404() throws Exception {
        when(gasForecastService.getForecast(anyInt()))
                .thenThrow(ForecastNotFoundException.class);

        mockMvc.perform(get("/gasforecast/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void getGasForecastShouldReturnHttpStatusOk200() throws Exception {
        when(gasForecastService.getForecast(anyInt()))
                .thenReturn(gasForecast);

        mockMvc.perform(get("/gasforecast/2"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getGasForecastShouldReturnJson() throws Exception {
        when(gasForecastService.getForecast(anyInt()))
                .thenReturn(gasForecast);

        mockMvc.perform(get("/gasforecast/2"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(objectToJson(gasForecast)));
    }

    @Test
    public void getGasForecastByTimeAndAddressShouldReturnHttpStatusOk200() throws Exception {
        when(gasForecastService.getForecast(any(LocalDateTime.class), any(String.class)))
                .thenReturn(gasForecast);

        mockMvc.perform(get("/gasforecast/get")
                .param("start", gasForecast.getStart().toString())
                .param("address", gasForecast.getAddress().getAddress()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getGasForecastByTimeAndAddressShouldReturnJson() throws Exception {
        when(gasForecastService.getForecast(any(LocalDateTime.class), any(String.class)))
                .thenReturn(gasForecast);

        mockMvc.perform(get("/gasforecast/get")
                .param("start", gasForecast.getStart().toString())
                .param("address", gasForecast.getAddress().getAddress()))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(objectToJson(gasForecast)));
    }

    @Test
    public void getGasForecastsByStartTimeShouldReturnHttpStatusOk200() throws Exception {
        when(gasForecastService.getForecastsByTime(any(LocalDateTime.class)))
                .thenReturn(Arrays.asList(gasForecast));

        mockMvc.perform(get("/gasforecast/getbystart")
                .param("start", gasForecast.getStart().toString()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getGasForecastsByStartTimeShouldReturnJson() throws Exception {
        when(gasForecastService.getForecastsByTime(any(LocalDateTime.class)))
                .thenReturn(Arrays.asList(gasForecast));

        mockMvc.perform(get("/gasforecast/getbystart")
                .param("start", gasForecast.getStart().toString()))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(objectToJson(Arrays.asList(gasForecast))));
    }

    @Test
    public void getCurrentGasForecastsShouldReturnHttpStatusOk200() throws Exception {
        when(gasForecastService.getCurrentGasForecasts(any(LocalDateTime.class)))
                .thenReturn(Arrays.asList(gasForecast));

        mockMvc.perform(get("/gasforecast/getcurrent"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getCurrentGasForecastsShouldReturnJson() throws Exception {
        gasForecast.setEstimatedStop(LocalDateTime.of(3000,1,1,0,0));
        when(gasForecastService.getCurrentGasForecasts(any(LocalDateTime.class)))
                .thenReturn(Arrays.asList(gasForecast));

        mockMvc.perform(get("/gasforecast/getcurrent"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(objectToJson(Arrays.asList(gasForecast))));
    }

    @Test
    public void getAddressesByTimeShouldReturnHttpStatusOk200() throws Exception {
        when(gasForecastService.getAddressesByTime(any(LocalDateTime.class)))
                .thenReturn(new HashSet(Arrays.asList(address)));

        mockMvc.perform(get("/gasforecast/getaddressesbytime")
                .param("start", gasForecast.getStart().toString()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getAddressesByTimeShouldReturnJson() throws Exception {
        when(gasForecastService.getAddressesByTime(any(LocalDateTime.class)))
                .thenReturn(new HashSet(Arrays.asList(address)));

        mockMvc.perform(get("/gasforecast/getaddressesbytime")
                .param("start", gasForecast.getStart().toString()))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(objectToJson(new HashSet(Arrays.asList(address)))));
    }

    @Test
    public void addNewGasForecastShouldReturnHttpStatusOk200() throws Exception {
        doNothing().when(gasForecastService).addNewGasForecast(any());

        mockMvc.perform(post("/gasforecast/addnew")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectToJson(gasForecast))
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void updateGasForecastShouldReturnHttpStatusOk200() throws Exception {
        doNothing().when(gasForecastService).updateGasForecast(any());

        mockMvc.perform(
                put("/gasforecast/update")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectToJson(gasForecast))
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void updateGasForecastShouldReturnHttpStatusNotFound404() throws Exception {
        doThrow(ForecastNotFoundException.class).when(gasForecastService).updateGasForecast(any());

        mockMvc.perform(
                put("/gasforecast/update")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectToJson(gasForecast))
        )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteGasForecastShouldReturnHttpStatusOk200() throws Exception {
        mockMvc.perform(
                delete("/gasforecast/delete")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectToJson(gasForecast))
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteGasForecastShouldReturnHttpStatusNotFound404() throws Exception {
        doThrow(ForecastNotFoundException.class).when(gasForecastService).deleteGasForecast(any());

        mockMvc.perform(
                delete("/gasforecast/delete")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectToJson(gasForecast))
        )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteGasForecastsByTimeShouldReturnHttpStatusOk200() throws Exception {
        mockMvc.perform(
                delete("/gasforecast/deleteallbytime")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectToJson(LocalDateTime.now()))
        )
                .andDo(print())
                .andExpect(status().isOk());
    }
}
