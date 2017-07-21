package com.cityelf;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cityelf.controller.AdvertisementController;
import com.cityelf.model.Address;
import com.cityelf.model.Advertisement;
import com.cityelf.service.AdvertisementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(value = AdvertisementController.class, secure = false)
public class AdvertisementControllerTest {

  List<Advertisement> advertisements;
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AdvertisementService advertisementService;

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

    mockMvc.perform(get("/alladvertisements/get")
        .param("addressid", String.valueOf(address.getId())))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void getAllAdvertisementsByAddressIdShouldReturJson() throws Exception {
    when(advertisementService.getAdvertisements(anyLong()))
        .thenReturn(advertisements);

    List<Advertisement> expectedList = new ArrayList<>();

    mockMvc.perform(get("/alladvertisements/get")
        .param("addressid", String.valueOf(address.getId())))
        .andDo(print())
        .andExpect(content().string(objectToJson(expectedList)));
  }
}
