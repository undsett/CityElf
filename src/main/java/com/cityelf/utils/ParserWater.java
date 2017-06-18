package com.cityelf.utils;

import com.cityelf.domain.ForcastData;
import com.cityelf.domain.Report;
import com.cityelf.exceptions.ParserUnavailableException;
import com.cityelf.exceptions.WaterParserUnavailableException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@Component
public class ParserWater {

  @Value("${infox.data.url}")
  private URL urlToParse;
  @Autowired
  private ContentLoader loader;
  @Autowired
  private WaterForcaster forcaster;

  public void setLoader(ContentLoader loader) {
    this.loader = loader;
  }

  public void setForcaster(WaterForcaster forcaster) {
    this.forcaster = forcaster;
  }

  private static <T> T toJavaObject(String json, Class<T> clazz)
      throws WaterParserUnavailableException {
    try {
      ObjectMapper mapper = new ObjectMapper();
      return mapper.readValue(json, clazz);
    } catch (IOException exception) {
      throw new WaterParserUnavailableException("Exception on parsing json", exception);
    }
  }

  public List<ForcastData> getForcastDataList() throws ParserUnavailableException {
    String content = loader.load(urlToParse);
    Report[] reports = toJavaObject(content, new Report[0].getClass());
    return forcaster.getForcastsData(reports);
  }
}
