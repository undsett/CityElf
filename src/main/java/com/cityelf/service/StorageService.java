package com.cityelf.service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;

@Service
public class StorageService {

  private Logger logger = LogManager.getLogger(getClass());
  private final String uploadFolderName = "uploaded";

  public void saveUploaded(InputStream inputStream, String originalFileName) {
    try {
      if (!Files.exists(Paths.get(uploadFolderName))) {
        Files.createDirectory(Paths.get(uploadFolderName));
      }
      Files.copy(inputStream,
          Paths.get(uploadFolderName,
              String.format("(%s)%s", Instant.now().getEpochSecond(), originalFileName)));
    } catch (IOException ex) {
      logger.error(ex.getMessage(), ex);
    }
  }
}
