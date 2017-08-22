package com.cityelf;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cityelf.controller.AdvertisementController;
import com.cityelf.exceptions.AdvertisementNotFoundException;
import com.cityelf.model.Address;
import com.cityelf.model.Advertisement;
import com.cityelf.service.AdvertisementService;
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

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(value = AdvertisementController.class, secure = false)
public class AdvertisementControllerTest {

  List<Advertisement> advertisements;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AdvertisementService advertisementService;
  private Advertisement advertisement = new Advertisement();
  private Address address = new Address();

  public String objectToJson(Object o) throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    return mapper.writeValueAsString(o);
  }

  @Before
  public void SetUp() {
    advertisements = new ArrayList<>();
  }

  @Test
  public void getAllAdvertisementsByAddressIdShouldReturnHttpStatusOk200() throws Exception {
    when(advertisementService.getAdvertisements(anyLong()))
        .thenReturn(advertisements);

    mockMvc.perform(get("/services/advertisements/getall")
        .param("addressid", String.valueOf(address.getId())))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void getAllAdvertisementsByAddressIdShouldReturJson() throws Exception {
    when(advertisementService.getAdvertisements(anyLong()))
        .thenReturn(advertisements);

    List<Advertisement> expectedList = new ArrayList<>();

    mockMvc.perform(get("/services/advertisements/getall")
        .param("addressid", String.valueOf(address.getId())))
        .andDo(print())
        .andExpect(content().string(objectToJson(expectedList)));
  }

  @Test
  public void getAdvertisementByIdShouldReturnHttpStatusOk200() throws Exception {
    advertisement.setAddress(address);
    when(advertisementService.getAdvertisementById(anyLong()))
        .thenReturn(advertisement);

    mockMvc.perform(get("/services/advertisements/getadvertisement")
        .param("id", String.valueOf(advertisement.getId())))

        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void getAdvertisementByIdShouldReturnHttpStatusNotFound404() throws Exception {
    when(advertisementService.getAdvertisementById(anyLong()))
        .thenThrow(AdvertisementNotFoundException.class);

    mockMvc.perform(get("/services/advertisements/getadvertisement")
        .param("id", String.valueOf(advertisement.getId())))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  public void addAdvertisementShouldReturnHttpStatusOk200()
      throws Exception {
    mockMvc.perform(post("/services/advertisements/admin/addadvertisement")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(objectToJson(advertisement))
    )
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void updateAdvertisementShouldReturnHttpStatusOk200() throws Exception {
    advertisement.setDescription("new descpiption");
    advertisement.setSubject("new subject");
    mockMvc.perform(
        put("/services/advertisements/admin/updateadvertisement")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(objectToJson(advertisement))
    )
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void updateAdvertisementShouldReturnHttpStatusNotFound404() throws Exception {
    doThrow(AdvertisementNotFoundException.class).when(advertisementService)
        .updateAdvertisement(any());
    advertisement.setSubject("new subject");
    mockMvc.perform(
        put("/services/advertisements/admin/updateadvertisement")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(objectToJson(advertisement))
    )
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  public void deleteAdvertisementShouldReturnHttpStatusOk200()
      throws Exception {
    mockMvc.perform(
        delete("/services/advertisements/admin/deleteadvertisement")
            .param("id", String.valueOf(advertisement.getId()))
    )
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void deleteAdvertisementShouldReturnHttpStatusNotFound404() throws Exception {
    doThrow(AdvertisementNotFoundException.class).when(advertisementService)
        .deleteAdvertisement(anyLong());
    mockMvc.perform(
        delete("/services/advertisements/admin/deleteadvertisement")
            .param("id", String.valueOf(advertisement.getId()))
    )
        .andDo(print())
        .andExpect(status().isNotFound());
  }
}
