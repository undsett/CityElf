package com.cityelf;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cityelf.controller.UserController;
import com.cityelf.exceptions.UserNotFoundException;
import com.cityelf.model.User;
import com.cityelf.service.MailSenderService;
import com.cityelf.service.StorageService;
import com.cityelf.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class, secure = false)
public class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private UserService userService;
  @MockBean
  private MailSenderService mailSenderService;
  @MockBean
  private StorageService storageService;
  private User user = new User();

  public String objectToJson(Object o) throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(o);
  }

//  @Test
//  public void getAll() throws Exception {
//    when(userService.getAll()).thenReturn(Arrays.asList(user));
//
//    mockMvc.perform(get("/users/all"))
//        .andDo(print())
//        .andExpect(content().string(objectToJson(Arrays.asList(user))));
//  }

  @Test
  public void updateUserShouldReturnHttpStatusNotFound404() throws Exception {
    doThrow(UserNotFoundException.class).when(userService).updateUser(any());
    user.setEmail("login@domain.com");
    mockMvc.perform(
        put("/services/users/updateuser")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(objectToJson(user))
    )
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  public void updateUserShouldReturnHttpStatusOk200() throws Exception {
    when(userService.updateUser(any())).thenReturn(user);
    user.setEmail("login@domain.com");
    mockMvc.perform(
        put("/services/users/updateuser")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(objectToJson(user))
    )
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void updateUserShouldReturnHttpStatusBadRequest400() throws Exception {
    when(userService.updateUser(any())).thenReturn(user);
    user.setEmail("invalid-login.domain.com");
    mockMvc.perform(
        put("/services/users/updateuser")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(objectToJson(user))
    )
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  public void getUserShouldReturnHttpStatusNotFound404() throws Exception {
    when(userService.getUser(anyInt()))
        .thenThrow(UserNotFoundException.class);

    mockMvc.perform(get("/services/users/1"))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  public void getUserShouldReturnHttpStatusOk200() throws Exception {
    when(userService.getUser(anyInt()))
        .thenReturn(user);

    mockMvc.perform(get("/services/users/2"))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void getUserShouldReturnJson() throws Exception {
    when(userService.getUser(anyInt()))
        .thenReturn(user);

    mockMvc.perform(get("/services/users/2"))
        .andDo(print())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().string(objectToJson(user)));
  }

  @Test
  public void deleteUserShouldReturnHttpStatusNotFound404() throws Exception {
    doThrow(UserNotFoundException.class).when(userService).deleteUser(anyInt());

    mockMvc.perform(delete("/services/users/1"))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  public void deleteUserShouldReturnHttpStatusOk200() throws Exception {
    mockMvc.perform(delete("/services/users/2"))
        .andDo(print())
        .andExpect(status().isOk());
  }
}
