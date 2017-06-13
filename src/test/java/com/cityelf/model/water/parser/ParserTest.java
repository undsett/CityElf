package com.cityelf.model.water.parser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.cityelf.exceptions.WaterParserUnavailableException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ComponentScan(basePackages = "com.cityelf.model.water.parser")
public class ParserTest {

  private Parser parser;
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
      "\"sTime\":\"13.06.2017 10:30\","
      +
      "\"sState\":\"fact\","
      +
      "\"rTime\":\"13.06.2017 17:00\","
      +
      "\"rState\":\"fact\"}]}]";

  @Before
  public void setUp() throws Exception {
    ApplicationContext context = new AnnotationConfigApplicationContext(ParserTest.class);
    parser = context.getBean(Parser.class);

    WaterContentLoader loader = mock(WaterContentLoader.class);
    when(loader.load(anyObject()))
        .thenReturn(json);
    parser.setLoader(loader);
  }

  @Test
  public void parserTest() throws WaterParserUnavailableException {
    List<WaterForcastData> forcastDataList = parser.getForcastDataList();

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
        .isEqualTo("[10, 4, 4/а]");
  }
}
