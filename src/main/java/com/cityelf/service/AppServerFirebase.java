package com.cityelf.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class AppServerFirebase {

  @Value("${firebase.authentication.key}")
  private String authentificationKeyFirebase;
  @Value("${firebase.apiUrlFcm}")
  private String apiUrlFcm;

  public void pushFCMNotification(String firebaseId, String titleMessage, String bodyMessage)
      throws Exception {

    URL url = new URL(apiUrlFcm);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

    connection.setUseCaches(false);
    connection.setDoInput(true);
    connection.setDoOutput(true);

    connection.setRequestMethod("POST");
    connection.setRequestProperty("Authorization", "key=" + authentificationKeyFirebase);
    connection.setRequestProperty("Content-Type", "application/json");

    JSONObject json = new JSONObject();
    json.put("to", firebaseId.trim());
    JSONObject fireMessage = new JSONObject();
    fireMessage.put("title", titleMessage);
    fireMessage.put("body", bodyMessage);
    json.put("notification", fireMessage);

    OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
    try {
      writer.write(json.toString());
      writer.flush();
    } catch (IOException exeption) {
      exeption.printStackTrace();
    } finally {
      writer.close();
    }
  }
}