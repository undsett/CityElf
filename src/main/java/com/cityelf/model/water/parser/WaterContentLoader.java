package com.cityelf.model.water.parser;

import com.cityelf.exceptions.WaterParserUnavailableException;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@Component
class WaterContentLoader {

  public String load(URL url) throws WaterParserUnavailableException {
    try {
      URLConnection conn = url.openConnection();

      StringBuilder sb = new StringBuilder();
      try (BufferedReader reader = new BufferedReader(
          new InputStreamReader(conn.getInputStream()))) {
        reader.lines().forEach(line -> sb.append(line));
      }
      return sb.toString();
    } catch (IOException exception) {
      throw new WaterParserUnavailableException("Error on accessing infox web resourse", exception);
    }
  }
}
