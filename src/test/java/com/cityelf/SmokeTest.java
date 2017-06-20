package com.cityelf;


import static org.assertj.core.api.Assertions.assertThat;

import com.cityelf.controller.NotificationController;
import com.cityelf.controller.UserController;
import com.cityelf.controller.WaterForecastController;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SmokeTest {

  @Autowired
  private NotificationController notificationController;
  @Autowired
  private UserController userController;
  @Autowired
  private WaterForecastController waterForecastController;

  @Test
  public void contextLoads() {
    assertThat(notificationController).isNotNull();
    assertThat(userController).isNotNull();
    assertThat(waterForecastController).isNotNull();
  }
}
