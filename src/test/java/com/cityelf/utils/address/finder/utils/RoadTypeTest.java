package com.cityelf.utils.address.finder.utils;

import static com.cityelf.utils.address.finder.utils.RoadType.AVENUE;
import static com.cityelf.utils.address.finder.utils.RoadType.BOULEVARD;
import static com.cityelf.utils.address.finder.utils.RoadType.DESCENT;
import static com.cityelf.utils.address.finder.utils.RoadType.LANE;
import static com.cityelf.utils.address.finder.utils.RoadType.LINE;
import static com.cityelf.utils.address.finder.utils.RoadType.STREET;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

public class RoadTypeTest {


  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void getRoadType1() throws Exception {
    assertThat(RoadType.getRoadType("8 Марта, 5")).isEqualTo(STREET);
  }

  @Test
  public void getRoadType2() throws Exception {
    assertThat(RoadType.getRoadType("ул. 8 Марта, 5")).isEqualTo(STREET);
  }

  @Test
  public void getRoadType3() throws Exception {
    assertThat(RoadType.getRoadType("улица 8 Марта, 5")).isEqualTo(STREET);
  }

  @Test
  public void getRoadType4() throws Exception {
    assertThat(RoadType.getRoadType("8 Марта 5-й переулок, 9")).isEqualTo(LANE);
  }

  @Test
  public void getRoadType5() throws Exception {
    assertThat(RoadType.getRoadType("пер. 8 Марта 5-й, 9")).isEqualTo(LANE);
  }

  @Test
  public void getRoadType6() throws Exception {
    assertThat(RoadType.getRoadType("8 Марта Спуск, 5")).isEqualTo(DESCENT);
  }

  @Test
  public void getRoadType7() throws Exception {
    assertThat(RoadType.getRoadType("сп. 8 Марта, 5")).isEqualTo(DESCENT);
  }

  @Test
  public void getRoadType8() throws Exception {
    assertThat(RoadType.getRoadType("Десантный бул., 1а")).isEqualTo(BOULEVARD);
  }

  @Test
  public void getRoadType9() throws Exception {
    assertThat(RoadType.getRoadType("Десантный бульвар, 1а")).isEqualTo(BOULEVARD);
  }

  @Test
  public void getRoadType10() throws Exception {
    assertThat(RoadType.getRoadType("Адмиральский проспект, 1а")).isEqualTo(AVENUE);
  }

  @Test
  public void getRoadType11() throws Exception {
    assertThat(RoadType.getRoadType("просп. Адмиральский, 1а")).isEqualTo(AVENUE);
  }

  @Test
  public void getRoadType12() throws Exception {
    assertThat(RoadType.getRoadType("8 Марта 5-линия, 3")).isEqualTo(LINE);
  }

  @Test
  public void getRoadType13() throws Exception {
    assertThat(RoadType.getRoadType("8 Марта 5-я линия, 3")).isEqualTo(LINE);
  }

  @Test
  public void getRoadType14() throws Exception {
    assertThat(RoadType.getRoadType("добровольского проспект")).isEqualTo(AVENUE);
  }

}