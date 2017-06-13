package com.cityelf.model.water.parser;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
@ComponentScan(basePackages = "com.cityelf.model.water.parser")
public class WaterForcasterTest {

  WaterForcaster waterForcaster;
  Report[] reports;

  @Before
  public void setUp() throws Exception {
    ApplicationContext context = new AnnotationConfigApplicationContext(WaterForcasterTest.class);
    waterForcaster = context.getBean(WaterForcaster.class);

    List<Report> reportList = new ArrayList<>();

    Report report = new Report();
    report.text = "Проведение аварийных работ по устранению течи  водопровода по адресу ПР. АКАДЕМИКА ГЛУШКО,17";
    report.places = new ArrayList<>();

    Place place1 = new Place();
    place1.address = "ИЛЬФА И ПЕТРОВА УЛ., 21-39 все дома";
    place1.endTime = "13.06.2017 20:00";
    place1.startTime = "13.06.2017 10:00";

    Place place2 = new Place();
    place2.address = "АКАДЕМИКА ВИЛЬЯМСА УЛ., 64-76/Б все дома";
    place2.endTime = "13.06.2017 20:00";
    place2.startTime = "13.06.2017 10:00";

    report.places.add(place1);
    report.places.add(place2);

    reportList.add(report);
    reports = reportList.toArray(new Report[0]);
  }

  @Test
  public void getForcastsData() throws Exception {
    List<WaterForcastData> forcastsData = waterForcaster.getForcastsData(reports);
    Assertions.assertThat(forcastsData).isNotEmpty();
  }

  @Test
  public void parseToDate() throws Exception {
    LocalDateTime localDateTime = waterForcaster.parseToDate("13.06.2017 20:00");
    assertThat(localDateTime.getDayOfMonth()).isEqualTo(13);
    assertThat(localDateTime.getYear()).isEqualTo(2017);
    assertThat(localDateTime.getMonthValue()).isEqualTo(6);
    assertThat(localDateTime.getHour()).isEqualTo(20);
    assertThat(localDateTime.getMinute()).isEqualTo(0);
  }

}