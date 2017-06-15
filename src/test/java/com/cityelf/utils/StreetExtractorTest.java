package com.cityelf.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StreetExtractorTest {

  @Autowired
  private StreetExtractor streetExtractor;

  @Test
  public void getStreetName1() throws Exception {
    String streetName = streetExtractor.getStreetName("БАЛКОВСКАЯ УЛ., 20 и 26 все дома");
    assertThat(streetName).isEqualTo("БАЛКОВСКАЯ УЛ.");
  }

  @Test
  public void getStreetName2() throws Exception {
    String streetName = streetExtractor
        .getStreetName("2-Й АЛЕКСАНДРА НЕВСКОГО ПЕР., 1-63 все дома");
    assertThat(streetName).isEqualTo("2-Й АЛЕКСАНДРА НЕВСКОГО ПЕР.");
  }
}