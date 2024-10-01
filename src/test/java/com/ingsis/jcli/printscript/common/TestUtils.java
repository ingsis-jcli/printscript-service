package com.ingsis.jcli.printscript.common;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class TestUtils {
  public static Optional<String> getStringFromFile(
      OperationType type, String testCase, FileType fileType) {
    try {
      String path = type.name + "/" + testCase + "/" + fileType.name + ".txt";
      URL resourceUrl = TestUtils.class.getClassLoader().getResource(path);
      if (resourceUrl == null) {
        throw new IOException("Resource not found: " + path);
      }
      Path filePath = Paths.get(resourceUrl.toURI());
      return Optional.of(Files.readString(filePath));
    } catch (IOException | URISyntaxException e) {
      e.printStackTrace();
      return Optional.empty();
    }
  }
}
