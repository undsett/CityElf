package com.cityelf.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
    assertThat(numbers).containsExactly("47", "48", "49", "50");
    numbers = numberExtractor.getNumbers("ОСИПОВА УЛ., 47 - 50/52 все дома");
    assertThat(numbers).containsExactly("47", "48", "49", "50");
  }

  @Test
  public void getNumbers3() throws Exception {
    Set<String> numbers = numberExtractor.getNumbers("МАЧТОВАЯ УЛ., 6 - 8 все дома");
    assertThat(numbers).containsExactly("6", "7", "8");
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
    assertThat(numbers).containsExactly("3", "4", "5");
    numbers = numberExtractor.getNumbers("2-Й АЛЕКСАНДРА НЕВСКОГО ПЕР., 3-5 все дома");
    assertThat(numbers).containsExactly("3", "4", "5");
  }

  @Test
  public void getNumbers6() throws Exception {
    Set<String> numbers = numberExtractor.getNumbers("ЧЕРНИГОВСКАЯ УЛ., 5-9а все дома");
    assertThat(numbers).containsExactly("5", "6", "7", "8", "9");
    numbers = numberExtractor.getNumbers("ЧЕРНИГОВСКАЯ УЛ., 5 - 9а все дома");
    assertThat(numbers).containsExactly("5", "6", "7", "8", "9");
  }

  @Test
  public void getNumbers7() throws Exception {
    Set<String> numbers = numberExtractor.getNumbers("ШИШКИНА УЛ., 48-52/2а все дома");
    assertThat(numbers).containsExactly("48", "49", "50", "51", "52");
    numbers = numberExtractor.getNumbers("ШИШКИНА УЛ., 48 - 52/2а все дома");
    assertThat(numbers).containsExactly("48", "49", "50", "51", "52");
  }

  @Test
  public void getNumbers8() throws Exception {
    Set<String> numbers = numberExtractor.getNumbers("РЕКОРДНАЯ УЛ., 68/1 все дома");
    assertThat(numbers).containsExactly("68");
  }

  @Test
  public void getNumbers9() throws Exception {
    Set<String> numbers = numberExtractor.getNumbers("АЛЕКСАНДРА НЕВСКОГО УЛ., 1-3/2 все дома");
    assertThat(numbers).containsExactly("1", "2", "3");
    numbers = numberExtractor.getNumbers("АЛЕКСАНДРА НЕВСКОГО УЛ., 1 - 3/2 все дома");
    assertThat(numbers).containsExactly("1", "2", "3");
  }

  @Test
  public void getNumbers10() throws Exception {
    Set<String> numbers = numberExtractor.getNumbers("АКАДЕМИКА ВИЛЬЯМСА УЛ., 64-76/Б все дома");
    assertThat(numbers)
        .containsExactly("66", "67", "68", "69", "70", "71", "72", "73", "74", "64", "75", "65",
            "76");
    numbers = numberExtractor.getNumbers("АКАДЕМИКА ВИЛЬЯМСА УЛ., 64 - 76/Б все дома");
    assertThat(numbers)
        .containsExactly("66", "67", "68", "69", "70", "71", "72", "73", "74", "64", "75", "65",
            "76");
  }

  @Test
  public void getNumbers11() throws Exception {
    Set<String> numbers = numberExtractor
        .getNumbers("МАРШАЛА ЖУКОВА ПР., 34,34а,101а,103 все дома");
    assertThat(numbers).contains("34", "101", "103");
    numbers = numberExtractor.getNumbers("МАРШАЛА ЖУКОВА ПР., 34, 34а, 101а , 103 все дома");
    assertThat(numbers).contains("34", "101", "103");
  }

  @Test
  public void getNumbers12() throws Exception {
    Set<String> numbers = numberExtractor.getNumbers("ТРАМВАЙНАЯ УЛ., 1 верх этажи-4 все дома");
    assertThat(numbers).containsExactly("1", "2", "3", "4");
    numbers = numberExtractor.getNumbers("ТРАМВАЙНАЯ УЛ., 1 верх этажи - 4 все дома");
    assertThat(numbers).containsExactly("1", "2", "3", "4");
  }

  @Test
  public void getNumbers13() throws Exception {
    Set<String> numbers = numberExtractor
        .getNumbers("ИЛЬФА И ПЕТРОВА УЛ., 1 верх этажи-3/1 все дома");
    assertThat(numbers).containsExactly("1", "2", "3");
    numbers = numberExtractor.getNumbers("ИЛЬФА И ПЕТРОВА УЛ., 1 верх этажи - 3/1 все дома");
    assertThat(numbers).containsExactly("1", "2", "3");
  }

  @Test
  public void getNumbers14() throws Exception {
    Set<String> numbers = numberExtractor
        .getNumbers("АКАДЕМИКА ГЛУШКО ПР., 1 верх этажи-3а все дома");
    assertThat(numbers).containsExactly("1", "2", "3");
    numbers = numberExtractor.getNumbers("АКАДЕМИКА ГЛУШКО ПР., 1 верх этажи - 3а все дома");
    assertThat(numbers).containsExactly("1", "2", "3");
  }

  @Test
  public void getNumbers15() throws Exception {
    Set<String> numbers = numberExtractor
        .getNumbers("ЛЮСТДОРФСКАЯ ДОРОГА УЛ., 162 верх этажи-178/1 все дома");
    assertThat(numbers).containsExactly("170", "171", "172", "162", "173", "163", "174",
        "164", "175", "165", "176", "166", "177", "167", "178", "168", "169");
    numbers = numberExtractor
        .getNumbers("ЛЮСТДОРФСКАЯ ДОРОГА УЛ., 162 верх этажи - 178/1 все дома");
    assertThat(numbers).containsExactly("170", "171", "172", "162", "173", "163", "174",
        "164", "175", "165", "176", "166", "177", "167", "178", "168", "169");
  }

  @Test
  public void getNumbers16() throws Exception {
    Set<String> numbers = numberExtractor.getNumbers("МАРШАЛА ЖУКОВА ПР., 10,4,4/а все дома");
    assertThat(numbers).contains("10", "4");
    numbers = numberExtractor.getNumbers("МАРШАЛА ЖУКОВА ПР., 10, 4 , 4/а все дома");
    assertThat(numbers).contains("10", "4");
  }

  @Test
  public void getNumbers17() throws Exception {
    Set<String> numbers = numberExtractor.getNumbers("ЧУБАЕВСКАЯ УЛ., 11/б все дома");
    assertThat(numbers).containsExactly("11");
    numbers = numberExtractor.getNumbers("ЧУБАЕВСКАЯ УЛ., 11/б все дома");
    assertThat(numbers).containsExactly("11");
  }

  @Test
  public void getNumbers18() throws Exception {
    Set<String> numbers = numberExtractor
        .getNumbers("АВДЕЕВА-ЧЕРНОМОРСКОГО УЛ., 1-2/а/б/в все дома");
    assertThat(numbers).containsExactly("1", "2");
    numbers = numberExtractor.getNumbers("АВДЕЕВА-ЧЕРНОМОРСКОГО УЛ., 1-2/а/б/в все дома");
    assertThat(numbers).containsExactly("1", "2");
  }
}