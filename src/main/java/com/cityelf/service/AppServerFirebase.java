package com.cityelf.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class AppServerFirebase {

  @Value("${authentificationKeyFirebase}")
  private String authentificationKeyFirebase;

  public void pushFCMNotification(String firebaseId, String titleMessage, String bodyMessage)
      throws Exception {

    String apiUrlFcm = "https://fcm.googleapis.com/fcm/send";

    URL url = new URL(apiUrlFcm);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

    conn.setUseCaches(false);
    conn.setDoInput(true);
    conn.setDoOutput(true);

    conn.setRequestMethod("POST");
    conn.setRequestProperty("Authorization", "key=" + authentificationKeyFirebase);
    conn.setRequestProperty("Content-Type", "application/json");

    JSONObject json = new JSONObject();
    json.put("to", firebaseId.trim());
    JSONObject fireMessage = new JSONObject();
    fireMessage.put("title", titleMessage);
    fireMessage.put("body", bodyMessage);
    json.put("notification", fireMessage);

    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
    wr.write(json.toString());
    wr.flush();
    conn.getInputStream();
  }
}