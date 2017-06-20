package com.cityelf;


import static org.assertj.core.api.Assertions.assertThat;

import com.cityelf.controller.ElectricityForecastController;
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
<<<<<<< HEAD
  private ElectricityForecastController electricityForecastController;
=======
  private WaterForecastController waterForecastController;
>>>>>>> 05555bd10359f5aa5136f261f944eafe1a5dcd5a

  @Test
  public void contextLoads() {
    assertThat(notificationController).isNotNull();
    assertThat(userController).isNotNull();
<<<<<<< HEAD
    assertThat(electricityForecastController).isNotNull();
=======
    assertThat(waterForecastController).isNotNull();
>>>>>>> 05555bd10359f5aa5136f261f944eafe1a5dcd5a
  }
}
