package com.cityelf;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cityelf.controller.NotificationController;
import com.cityelf.exceptions.UserNotFoundException;
import com.cityelf.model.Notification;
import com.cityelf.service.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

@RunWith(SpringRunner.class)
@WebMvcTest(value = NotificationController.class, secure = false)
public class NotificationControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private NotificationService notificationService;
  private Notification notification = new Notification();

  public String objectToJson(Object o) throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(o);
  }

  @Test
  public void getNotificationShouldReturnHttpStatusNotFound404() throws Exception {
    when(notificationService.getNotification(anyInt()))
        .thenThrow(UserNotFoundException.class);

    mockMvc.perform(get("/services/notification/settings/1"))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  public void getNotificationShouldReturnHttpStatusOk200() throws Exception {
    when(notificationService.getNotification(anyInt()))
        .thenReturn(Optional.of(notification));

    mockMvc.perform(get("/services/notification/settings/2"))
        .andDo(print())
        .andExpect(status().isOk());

  }

  @Test
  public void getNotificationShouldReturnJson() throws Exception {
    when(notificationService.getNotification(anyInt()))
        .thenReturn(Optional.of(notification));

    mockMvc.perform(get("/services/notification/settings/3"))
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith("application/json"))
        .andExpect(content().string(objectToJson(notification)));
  }
}
