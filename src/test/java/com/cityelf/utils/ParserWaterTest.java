package com.cityelf.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.cityelf.domain.ForcastData;
import com.cityelf.exceptions.ParserUnavailableException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParserWaterTest {

  @Autowired
  private ParserWater parserWater;
  private String json = "[{"
      +
      "\"text\":\"Проведение аварийных работ по устранению течи  водопровода по адресу МАРШАЛА ЖУКОВА ПР. 100 \","
      +
      "\"places\":[{"
      +
      "\"n\":1,"
      +
      "\"address\":\"МАРШАЛА ЖУКОВА ПР., 10,4,4/а все дома\","
      +
      "\"state\":\"complete\","
      +
      "\"stime\":\"13.06.2017 10:30\","
      +
      "\"sstate\":\"fact\","
      +
      "\"rtime\":\"13.06.2017 17:00\","
      +
      "\"rstate\":\"fact\"}]}]";

  @Before
  public void setUp() throws Exception {
    ContentLoader loader = mock(ContentLoader.class);
    when(loader.load(anyObject()))
        .thenReturn(json);
    parserWater.setLoader(loader);
  }

  @Test
  public void parserTest() throws ParserUnavailableException {
    List<ForcastData> forcastDataList = parserWater.getForcastDataList();

    assertThat(forcastDataList.size())
        .isEqualTo(1);
    assertThat(forcastDataList.get(0).getAdress())
        .isEqualTo("МАРШАЛА ЖУКОВА ПР.");
    assertThat(forcastDataList.get(0).getRawAdress())
        .isEqualTo("МАРШАЛА ЖУКОВА ПР., 10,4,4/а все дома");
    assertThat(forcastDataList.get(0).getEndOff().toString())
        .isEqualTo("2017-06-13T17:00");
    assertThat(forcastDataList.get(0).getStartOff().toString())
        .isEqualTo("2017-06-13T10:30");
    assertThat(forcastDataList.get(0).getBuildingNumberList().toString())
        .contains("4", "4", "4-а");
  }
}
