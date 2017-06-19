package com.cityelf.utils;

import com.cityelf.exceptions.ParserUnavailableException;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

@Component
class ContentLoader {


  public String load(URL url)
      throws ParserUnavailableException {
    try {
      TrustManager[] trustAllCerts = new TrustManager[]{
          new X509TrustManager() {
            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
              return null;
            }

            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] certs,
                String authType) {
            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] certs,
                String authType) {
            }
          }
      };

      SSLContext sc = SSLContext.getInstance("SSL");
      sc.init(null, trustAllCerts, new java.security.SecureRandom());
      HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

      URLConnection conn = url.openConnection();

      StringBuilder sb = new StringBuilder();
      try (BufferedReader reader = new BufferedReader(
          new InputStreamReader(conn.getInputStream()))) {
        reader.lines().forEach(line -> sb.append(line));
      }
      return sb.toString().toLowerCase();
    } catch (Exception ex) {
      throw new ParserUnavailableException(
          "Error while processing " + url.getHost(), ex);
    }
  }
}
