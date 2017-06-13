package com.cityelf.model.water.parser;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.cityelf.model.water.parser")
public class StreetExtractorTest {

  @Autowired
  private StreetExtractor streetExtractor;

  @Before
  public void setUp() throws Exception {
    ApplicationContext context = new AnnotationConfigApplicationContext(StreetExtractorTest.class);
    streetExtractor = context.getBean(StreetExtractor.class);
  }

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