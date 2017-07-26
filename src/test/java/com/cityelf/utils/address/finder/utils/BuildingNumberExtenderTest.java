package com.cityelf.utils.address.finder.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Set;


public class BuildingNumberExtenderTest {

  private BuildingNumberExtender numberExtender;

  @Before
  public void setUp() throws Exception {
    numberExtender = new BuildingNumberExtender();
  }

  @Test
  public void getExactNumbers1() throws Exception {
    Set<String> exactNumbers = numberExtender.getNumbers(Arrays.asList("12/б"));
    assertTrue(exactNumbers.contains("12-б"));
  }

  @Test
  public void getExactNumbers2() throws Exception {
    Set<String> exactNumbers = numberExtender.getNumbers(Arrays.asList("1-3б"));
    assertTrue(exactNumbers.contains("1+"));
    assertTrue(exactNumbers.contains("2+"));
    assertTrue(exactNumbers.contains("3-б"));
  }

  @Test
  public void getExactNumbers3() throws Exception {
    Set<String> exactNumbers = numberExtender.getNumbers(Arrays.asList("5-7/1б"));
    assertTrue(exactNumbers.contains("5+"));
    assertTrue(exactNumbers.contains("6+"));
    assertTrue(exactNumbers.contains("7-1б"));
  }

  @Test
  public void getExactNumbers4() throws Exception {
    Set<String> exactNumbers = numberExtender.getNumbers(Arrays.asList("22"));
    assertTrue(exactNumbers.contains("22"));
  }

  @Test
  public void getExactNumber1() throws Exception {
    assertEquals("2-б", numberExtender.getNumber("2/б").get());
  }

  @Test
  public void getExactNumber2() throws Exception {
    assertEquals("2-б", numberExtender.getNumber("2б").get());
  }

  @Test
  public void getExactNumber3() throws Exception {
    assertEquals("2-1б", numberExtender.getNumber("2/1б").get());
  }

  @Test
  public void getExactNumber4() throws Exception {
    assertEquals("2", numberExtender.getNumber("2").get());
  }

}