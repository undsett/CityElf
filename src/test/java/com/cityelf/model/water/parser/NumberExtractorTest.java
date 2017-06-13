package com.cityelf.model.water.parser;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
@ComponentScan(basePackages = "com.cityelf.model.water.parser")
public class NumberExtractorTest {

  NumberExtractor numberExtractor = new NumberExtractor();

  @Before
  public void setUp() throws Exception {
    ApplicationContext context = new AnnotationConfigApplicationContext(NumberExtractorTest.class);
    numberExtractor = context.getBean(NumberExtractor.class);
  }

  @Test
  public void getNumbers1() throws Exception {
    Set<String> numbers = numberExtractor.getNumbers("БАЛКОВСКАЯ УЛ., 20 и 26 все дома");
    assertThat(numbers).contains("20", "26");
  }

  @Test
  public void getNumbers2() throws Exception {
    Set<String> numbers = numberExtractor.getNumbers("ОСИПОВА УЛ., 37-50/52 все дома");
    assertThat(numbers).containsExactly("37-50/52");
    numbers = numberExtractor.getNumbers("ОСИПОВА УЛ., 37 - 50/52 все дома");
    assertThat(numbers).containsExactly("37-50/52");
  }

  @Test
  public void getNumbers3() throws Exception {
    Set<String> numbers = numberExtractor.getNumbers("МАЧТОВАЯ УЛ., 6 - 26 все дома");
    assertThat(numbers).containsExactly("6-26");
  }

  @Test
  public void getNumbers4() throws Exception {
    Set<String> numbers = numberExtractor
        .getNumbers("АКАДЕМИКА ВОРОБЬЁВА УЛ., р-н СЛОБОДКА все дома");
    assertThat(numbers).isEmpty();
  }

  @Test
  public void getNumbers5() throws Exception {
    Set<String> numbers = numberExtractor.getNumbers("2-Й АЛЕКСАНДРА НЕВСКОГО ПЕР., 1-63 все дома");
    assertThat(numbers).containsExactly("1-63");
    numbers = numberExtractor.getNumbers("2-Й АЛЕКСАНДРА НЕВСКОГО ПЕР., 1 - 63 все дома");
    assertThat(numbers).containsExactly("1-63");
  }

  @Test
  public void getNumbers6() throws Exception {
    Set<String> numbers = numberExtractor.getNumbers("ЧЕРНИГОВСКАЯ УЛ., 5-9а все дома");
    assertThat(numbers).containsExactly("5-9а");
    numbers = numberExtractor.getNumbers("ЧЕРНИГОВСКАЯ УЛ., 5 - 9а все дома");
    assertThat(numbers).containsExactly("5-9а");
  }

  @Test
  public void getNumbers7() throws Exception {
    Set<String> numbers = numberExtractor.getNumbers("ШИШКИНА УЛ., 48-52/2а все дома");
    assertThat(numbers).containsExactly("48-52/2а");
    numbers = numberExtractor.getNumbers("ШИШКИНА УЛ., 48 - 52/2а все дома");
    assertThat(numbers).containsExactly("48-52/2а");
  }

  @Test
  public void getNumbers8() throws Exception {
    Set<String> numbers = numberExtractor.getNumbers("РЕКОРДНАЯ УЛ., 68/1 все дома");
    assertThat(numbers).containsExactly("68/1");
  }

  @Test
  public void getNumbers9() throws Exception {
    Set<String> numbers = numberExtractor.getNumbers("АЛЕКСАНДРА НЕВСКОГО УЛ., 1-39/2 все дома");
    assertThat(numbers).containsExactly("1-39/2");
    numbers = numberExtractor.getNumbers("АЛЕКСАНДРА НЕВСКОГО УЛ., 1 - 39/2 все дома");
    assertThat(numbers).containsExactly("1-39/2");
  }

  @Test
  public void getNumbers10() throws Exception {
    Set<String> numbers = numberExtractor.getNumbers("АКАДЕМИКА ВИЛЬЯМСА УЛ., 64-76/Б все дома");
    assertThat(numbers).containsExactly("64-76/Б");
    numbers = numberExtractor.getNumbers("АКАДЕМИКА ВИЛЬЯМСА УЛ., 64 - 76/Б все дома");
    assertThat(numbers).containsExactly("64-76/Б");
  }
///////////////////////////

  @Test
  public void getNumbers11() throws Exception {
    Set<String> numbers = numberExtractor
        .getNumbers("МАРШАЛА ЖУКОВА ПР., 34,34а,101а,103 все дома");
    assertThat(numbers).contains("34", "34а", "101а", "103");
    numbers = numberExtractor.getNumbers("МАРШАЛА ЖУКОВА ПР., 34, 34а, 101а , 103 все дома");
    assertThat(numbers).contains("34", "34а", "101а", "103");
  }

  @Test
  public void getNumbers12() throws Exception {
    Set<String> numbers = numberExtractor.getNumbers("ТРАМВАЙНАЯ УЛ., 1 верх этажи-24 все дома");
    assertThat(numbers).containsExactly("1-24");
    numbers = numberExtractor.getNumbers("ТРАМВАЙНАЯ УЛ., 1 верх этажи - 24 все дома");
    assertThat(numbers).containsExactly("1-24");
  }

  @Test
  public void getNumbers13() throws Exception {
    Set<String> numbers = numberExtractor
        .getNumbers("ИЛЬФА И ПЕТРОВА УЛ., 1 верх этажи-63/1 все дома");
    assertThat(numbers).containsExactly("1-63/1");
    numbers = numberExtractor.getNumbers("ИЛЬФА И ПЕТРОВА УЛ., 1 верх этажи - 63/1 все дома");
    assertThat(numbers).containsExactly("1-63/1");
  }

  @Test
  public void getNumbers14() throws Exception {
    Set<String> numbers = numberExtractor
        .getNumbers("АКАДЕМИКА ГЛУШКО ПР., 1 верх этажи-29а все дома");
    assertThat(numbers).containsExactly("1-29а");
    numbers = numberExtractor.getNumbers("АКАДЕМИКА ГЛУШКО ПР., 1 верх этажи - 29а все дома");
    assertThat(numbers).containsExactly("1-29а");
  }

  @Test
  public void getNumbers15() throws Exception {
    Set<String> numbers = numberExtractor
        .getNumbers("ЛЮСТДОРФСКАЯ ДОРОГА УЛ., 162 верх этажи-178/1 все дома");
    assertThat(numbers).containsExactly("162-178/1");
    numbers = numberExtractor
        .getNumbers("ЛЮСТДОРФСКАЯ ДОРОГА УЛ., 162 верх этажи - 178/1 все дома");
    assertThat(numbers).containsExactly("162-178/1");
  }

  @Test
  public void getNumbers16() throws Exception {
    Set<String> numbers = numberExtractor.getNumbers("МАРШАЛА ЖУКОВА ПР., 10,4,4/а все дома");
    assertThat(numbers).contains("10", "4", "4/а");
    numbers = numberExtractor.getNumbers("МАРШАЛА ЖУКОВА ПР., 10, 4 , 4/а все дома");
    assertThat(numbers).contains("10", "4", "4/а");
  }

  @Test
  public void getNumbers17() throws Exception {
    Set<String> numbers = numberExtractor.getNumbers("ЧУБАЕВСКАЯ УЛ., 11/б все дома");
    assertThat(numbers).containsExactly("11/б");
    numbers = numberExtractor.getNumbers("ЧУБАЕВСКАЯ УЛ., 11/б все дома");
    assertThat(numbers).containsExactly("11/б");
  }

  @Test
  public void getNumbers18() throws Exception {
    Set<String> numbers = numberExtractor
        .getNumbers("АВДЕЕВА-ЧЕРНОМОРСКОГО УЛ., 1-27/а/б/в все дома");
    assertThat(numbers).containsExactly("1-27/а/б/в");
    numbers = numberExtractor.getNumbers("АВДЕЕВА-ЧЕРНОМОРСКОГО УЛ., 1-27/а/б/в все дома");
    assertThat(numbers).containsExactly("1-27/а/б/в");
  }
}