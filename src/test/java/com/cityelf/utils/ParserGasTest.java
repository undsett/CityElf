package com.cityelf.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.cityelf.domain.ForcastData;

import org.jsoup.Jsoup;
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
public class ParserGasTest {

  @Autowired
  private ParserGas parserGas;

  private String content;

  @Before
  public void setUp() throws Exception {
    File file = new File(".\\src\\test\\java\\com\\cityelf\\utils\\gasWebResponseTest.txt");
    StringBuilder sb = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(
        new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-8")))) {
      reader.lines().forEach(sb::append);
    }
    content = sb.toString().toLowerCase();
    GasLoader loader = mock(GasLoader.class);
    when(loader.getNeededNews(anyString(), anyString()))
        .thenReturn(Jsoup.parseBodyFragment(content).select("div.news-content"));
    parserGas.setLoader(loader);
  }

  @Test
  public void getForcastDataList() throws Exception {
    List<ForcastData> forcastDataList = parserGas.getForcastDataList();
    for (ForcastData forcastData : forcastDataList) {
      System.out.println(forcastData);
    }
    assertThat(forcastDataList.size()).isEqualTo(4);
  }
}
