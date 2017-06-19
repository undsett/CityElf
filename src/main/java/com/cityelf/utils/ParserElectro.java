package com.cityelf.utils;

import com.cityelf.domain.ForcastData;
import com.cityelf.exceptions.ParserUnavailableException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class ParserElectro {

  @Value("${oblenergo.url.template}")
  private String urlTemplate;
  @Value("${oblenergo.regions}")
  private int[] regions;
  @Autowired
  private ContentLoader loader;
  @Autowired
  private ElectroForcaster forcaster;

  public void setLoader(ContentLoader loader) {
    this.loader = loader;
  }

  public List<ForcastData> getForcastDataList() throws ParserUnavailableException {
    List<ForcastData> forcastDataList = new ArrayList<>();
    try {
      for (int region : regions) {
        URL url = new URL(
            MessageFormat.format(urlTemplate, region, getFormatedDateToday()));
        String rawWebContent = loader.load(url);
        forcastDataList.addAll(forcaster.getForcastsData(rawWebContent));
      }

    } catch (MalformedURLException ex) {
      ex.printStackTrace();
    }
    return forcastDataList;
  }

  private String getFormatedDateToday() {
    LocalDate localDate = LocalDate.now(ZoneId.of("UTC+3"));
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    return localDate.format(formatter);
  }
}