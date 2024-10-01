package com.ingsis.jcli.printscript.common;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.Report;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public class PrintScriptUtil {
  public static InputStream getInputStreamFromString(String snippet) {
    return new ByteArrayInputStream(snippet.getBytes());
  }

  public static JsonObject getJsonRules(String config) {
    return JsonParser.parseString(config).getAsJsonObject();
  }

  public static String reportToString(Report report) {
    StringBuilder sb = new StringBuilder();
    Optional<List<String>> messages = report.getReport();
    if (messages.isEmpty()) {
      return "";
    }
    List<String> messageList = messages.get();
    for (int i = 0; i < messageList.size(); i++) {
      sb.append(messageList.get(i));
      if (i < messageList.size() - 1) {
        sb.append("\n");
      }
    }
    return sb.toString();
  }
}
