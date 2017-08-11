package com.cityelf.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.cityelf.domain.ForcastData;
import com.cityelf.utils.parser.gas.utils.GasLoader;

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
import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParserGasTest {

  @Autowired
  private ParserGas parserGas;

  private String content;

  @Before
  public void setUp() throws Exception {
    StringBuilder stringBuilder = new StringBuilder();
    ClassLoader classLoader = getClass().getClassLoader();
    File gasFile = new File(classLoader.getResource("gasWebResponseTest.txt").getFile());
    BufferedReader reader = new BufferedReader(
        new InputStreamReader(new FileInputStream(gasFile), Charset.forName("UTF-8")));
    String line;
    while ((line = reader.readLine()) != null) {
      stringBuilder.append(line).append("\n");
    }
    reader.close();
    content = stringBuilder.toString();
    GasLoader loader = mock(GasLoader.class);
    when(loader.getNeededNews(anyString(), anyString()))
        .thenReturn(Jsoup.parseBodyFragment(content).select("div.news-content"));
    parserGas.setLoader(loader);
  }

  @Test
  public void getForcastDataList() throws Exception {
    List<ForcastData> forcastDataList = parserGas.getForcastDataList();
    assertThat(forcastDataList.size()).isEqualTo(4);
  }

  @Test
  public void startDateShouldBeSpecified() throws Exception {
    ForcastData gotData = parserGas.getForcastDataList().get(0);
    assertThat(gotData.getStartOff())
        .isEqualTo(LocalDateTime.of(2017,6,21,8,30));
  }

  @Test
  public void endDateShouldBeSpecified() throws Exception {
    ForcastData gotData = parserGas.getForcastDataList().get(0);
    assertThat(gotData.getEndOff())
        .isEqualTo(LocalDateTime.of(2017,6,21,17,30));
  }
}
