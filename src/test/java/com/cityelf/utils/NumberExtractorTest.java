package com.cityelf.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NumberExtractorTest {

  @Autowired
  NumberExtractor numberExtractor;

  @Test
  public void getNumbers1() throws Exception {
    Set<String> numbers = numberExtractor.getNumbers("БАЛКОВСКАЯ УЛ., 20 и 26 все дома");
    assertThat(numbers).contains("20", "26");
  }

  @Test
  public void getNumbers2() throws Exception {
    Set<String> numbers = numberExtractor.getNumbers("ОСИПОВА УЛ., 47-50/52 все дома");
    assertThat(numbers).contains("47+", "48+", "49+", "50-52");
    numbers = numberExtractor.getNumbers("ОСИПОВА УЛ., 47 - 50/52 все дома");
    assertThat(numbers).contains("47+", "48+", "49+", "50-52");
  }

  @Test
  public void getNumbers3() throws Exception {
    Set<String> numbers = numberExtractor.getNumbers("МАЧТОВАЯ УЛ., 6 - 8 все дома");
    assertThat(numbers).contains("6+", "7+", "8");
  }

  @Test
  public void getNumbers4() throws Exception {
    Set<String> numbers = numberExtractor
        .getNumbers("АКАДЕМИКА ВОРОБЬЁВА УЛ., р-н СЛОБОДКА все дома");
    assertThat(numbers).isEmpty();
  }

  @Test
  public void getNumbers5() throws Exception {
    Set<String> numbers = numberExtractor.getNumbers("2-Й АЛЕКСАНДРА НЕВСКОГО ПЕР., 3-5 все дома");
    assertThat(numbers).contains("3+", "4+", "5");
    numbers = numberExtractor.getNumbers("2-Й АЛЕКСАНДРА НЕВСКОГО ПЕР., 3-5 все дома");
    assertThat(numbers).contains("3+", "4+", "5");
  }

  @Test
  public void getNumbers6() throws Exception {
    Set<String> numbers = numberExtractor.getNumbers("ЧЕРНИГОВСКАЯ УЛ., 5-9а все дома");
    assertThat(numbers).contains("5+", "6+", "7+", "8+", "9-а");
    numbers = numberExtractor.getNumbers("ЧЕРНИГОВСКАЯ УЛ., 5 - 9а все дома");
    assertThat(numbers).contains("5+", "6+", "7+", "8+", "9-а");
  }

  @Test
  public void getNumbers7() throws Exception {
    Set<String> numbers = numberExtractor.getNumbers("ШИШКИНА УЛ., 48-52/2а все дома");
    assertThat(numbers).contains("48+", "49+", "50+", "51+", "52-2а");
    numbers = numberExtractor.getNumbers("ШИШКИНА УЛ., 48 - 52/2а все дома");
    assertThat(numbers).contains("48+", "49+", "50+", "51+", "52-2а");
  }

  @Test
  public void getNumbers8() throws Exception {
    Set<String> numbers = numberExtractor.getNumbers("РЕКОРДНАЯ УЛ., 68/1 все дома");
    assertThat(numbers).containsExactly("68-1");
  }

  @Test
  public void getNumbers9() throws Exception {
    Set<String> numbers = numberExtractor.getNumbers("АЛЕКСАНДРА НЕВСКОГО УЛ., 1-3/2 все дома");
    assertThat(numbers).contains("1+", "2+", "3-2");
    numbers = numberExtractor.getNumbers("АЛЕКСАНДРА НЕВСКОГО УЛ., 1 - 3/2 все дома");
    assertThat(numbers).contains("1+", "2+", "3-2");
  }

  @Test
  public void getNumbers10() throws Exception {
    Set<String> numbers = numberExtractor.getNumbers("АКАДЕМИКА ВИЛЬЯМСА УЛ., 70-76/Б все дома");
    assertThat(numbers)
        .contains("70+", "71+", "72+", "73+", "74+", "75+", "76-Б");
    numbers = numberExtractor.getNumbers("АКАДЕМИКА ВИЛЬЯМСА УЛ., 64 - 76/Б все дома");
    assertThat(numbers)
        .contains("70+", "71+", "72+", "73+", "74+", "75+", "76-Б");
  }

  @Test
  public void getNumbers11() throws Exception {
    Set<String> numbers = numberExtractor
        .getNumbers("МАРШАЛА ЖУКОВА ПР., 34,34а,101а,103 все дома");
    assertThat(numbers).contains("34", "34-а", "101-а", "103");
    numbers = numberExtractor.getNumbers("МАРШАЛА ЖУКОВА ПР., 34, 34а, 101а , 103 все дома");
    assertThat(numbers).contains("34", "34-а", "101-а", "103");
  }

  @Test
  public void getNumbers12() throws Exception {
    Set<String> numbers = numberExtractor.getNumbers("ТРАМВАЙНАЯ УЛ., 1 верх этажи-4 все дома");
    assertThat(numbers).contains("1+", "2+", "3+", "4");
    numbers = numberExtractor.getNumbers("ТРАМВАЙНАЯ УЛ., 1 верх этажи - 4 все дома");
    assertThat(numbers).contains("1+", "2+", "3+", "4");
  }

  @Test
  public void getNumbers13() throws Exception {
    Set<String> numbers = numberExtractor
        .getNumbers("ИЛЬФА И ПЕТРОВА УЛ., 1 верх этажи-3/1 все дома");
    assertThat(numbers).contains("1+", "2+", "3-1");
    numbers = numberExtractor.getNumbers("ИЛЬФА И ПЕТРОВА УЛ., 1 верх этажи - 3/1 все дома");
    assertThat(numbers).contains("1+", "2+", "3-1");
  }

  @Test
  public void getNumbers14() throws Exception {
    Set<String> numbers = numberExtractor
        .getNumbers("АКАДЕМИКА ГЛУШКО ПР., 1 верх этажи-3а все дома");
    assertThat(numbers).contains("1+", "2+", "3-а");
    numbers = numberExtractor.getNumbers("АКАДЕМИКА ГЛУШКО ПР., 1 верх этажи - 3а все дома");
    assertThat(numbers).contains("1+", "2+", "3-а");
  }

  @Test
  public void getNumbers15() throws Exception {
    Set<String> numbers = numberExtractor
        .getNumbers("ЛЮСТДОРФСКАЯ ДОРОГА УЛ., 162 верх этажи-163/1 все дома");
    assertThat(numbers).contains("162+", "163-1");
    numbers = numberExtractor
        .getNumbers("ЛЮСТДОРФСКАЯ ДОРОГА УЛ., 162 верх этажи - 163/1 все дома");
    assertThat(numbers).contains("162+", "163-1");
  }

  @Test
  public void getNumbers16() throws Exception {
    Set<String> numbers = numberExtractor.getNumbers("МАРШАЛА ЖУКОВА ПР., 10,4,4/а все дома");
    assertThat(numbers).contains("10", "4", "4-а");
    numbers = numberExtractor.getNumbers("МАРШАЛА ЖУКОВА ПР., 10, 4 , 4/а все дома");
    assertThat(numbers).contains("10", "4", "4-а");
  }

  @Test
  public void getNumbers17() throws Exception {
    Set<String> numbers = numberExtractor.getNumbers("ЧУБАЕВСКАЯ УЛ., 11/б все дома");
    assertThat(numbers).contains("11-б");
    numbers = numberExtractor.getNumbers("ЧУБАЕВСКАЯ УЛ., 11/б все дома");
    assertThat(numbers).contains("11-б");
  }

  @Test
  public void getNumbers18() throws Exception {
    Set<String> numbers = numberExtractor
        .getNumbers("АВДЕЕВА-ЧЕРНОМОРСКОГО УЛ., 1-2/а/б/в все дома");
    assertThat(numbers).contains("1+", "2-а-б-в");
    numbers = numberExtractor.getNumbers("АВДЕЕВА-ЧЕРНОМОРСКОГО УЛ., 1-2/а/б/в все дома");
    assertThat(numbers).contains("1+", "2-а-б-в");
  }

  @Test
  public void getNumbers19() throws Exception {
    Optional<String> number = numberExtractor.getNumber("Ивана Франко, 19 к2");
    assertThat(number.orElse("")).isEqualTo("19");
  }

  @Test
  public void getNumbers20() throws Exception {
    Optional<String> number = numberExtractor.getNumber("Ивана Франко, 19");
    assertThat(number.orElse("")).isEqualTo("19");
  }

  @Test
  public void getNumbers21() throws Exception {
    Optional<String> number = numberExtractor.getNumber("Ивана Франко, 19/к2");
    assertThat(number.orElse("")).isEqualTo("19-к2");
  }

  @Test
  public void getNumbers22() throws Exception {
    Optional<String> number = numberExtractor.getNumber("Ивана Франко, 19к2");
    assertThat(number.orElse("")).isEqualTo("19-к2");
  }

  @Test
  public void getNumbers23() throws Exception {
    Optional<String> number = numberExtractor.getNumber("1-линия Ивана Франко, 19к2");
    assertThat(number.orElse("")).isEqualTo("19-к2");
  }

  @Test
  public void getNumbers24() throws Exception {
    Set<String> numbers = numberExtractor.getNumbers("Жилые дома №: 35, 7, 7а, 5/24");
    assertThat(numbers).contains("35", "7", "7-а", "5-24");
  }
}