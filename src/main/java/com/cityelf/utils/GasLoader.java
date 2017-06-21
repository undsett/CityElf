package com.cityelf.utils;

import com.cityelf.exceptions.GasPageStructureChangedException;
import com.cityelf.exceptions.ParserUnavailableException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
class GasLoader {

  @Value("${odgaz.url}")
  private String webPage;

  Elements getNeededNews(String dateToCheck, String newsTheme)
      throws ParserUnavailableException {
    Elements neededNews = new Elements();
    Element newsHeader;
    String newsHeaderText;
    for (Element newsPiece : this.findNewsBlock()) {
      newsHeader = newsPiece.select("div.news-text-preview").first();
      if (newsHeader == null) {
        throw new GasPageStructureChangedException("No news-text-preview block");
      }
      newsHeaderText = newsHeader.text();
      if ((newsHeaderText.contains(dateToCheck))
          && (newsHeaderText.toLowerCase().contains(newsTheme))) {
        neededNews.add(newsPiece);
      }
    }
    return neededNews;
  }

  private Document loadDocument() throws ParserUnavailableException {
    Document loaded;
    try {
      loaded = Jsoup.connect(webPage).get();
    } catch (Exception ex) {
      throw new ParserUnavailableException(ex.getMessage(), ex.getCause());
    }
    return loaded;
  }

  private Elements findNewsBlock() throws ParserUnavailableException {
    Elements newsBlock = this.loadDocument().select("div.news-content");
    if (newsBlock.isEmpty()) {
      throw new GasPageStructureChangedException("No news block found");
    }
    return newsBlock;
  }
}
