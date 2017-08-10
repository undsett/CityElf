package com.cityelf.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.cityelf.domain.ForcastData;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParserElectroTest {

  @Autowired
  private ParserElectro parser;
  private String response;

  @Before
  public void setUp() throws Exception {
    File file = new File(getClass()
        .getClassLoader()
        .getResource("electroWebResponseTest.txt")
        .getFile());
    StringBuilder sb = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(
        new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-16")))) {
      reader.lines().forEach(l -> sb.append(l));
    }
    response = sb.toString().toLowerCase();
    ContentLoader loader = mock(ContentLoader.class);
    when(loader.load(any())).thenReturn(response);
    parser.setLoader(loader);
  }

  @Test
  public void getForcastDataList() throws Exception {
    List<ForcastData> forcastDataList = parser.getForcastDataList();
    for (ForcastData forcastData : forcastDataList) {
      System.out.println(forcastData);
    }
    assertThat(forcastDataList.size()).isEqualTo(78);
  }

}