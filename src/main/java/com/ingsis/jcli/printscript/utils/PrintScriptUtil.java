package com.ingsis.jcli.printscript.utils;

import com.google.gson.JsonObject;
import com.ingsis.jcli.printscript.common.requests.RuleDto;
import edu.Report;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public class PrintScriptUtil {
  public static InputStream getInputStreamFromString(String snippet) {
    return new ByteArrayInputStream(snippet.getBytes());
  }

  public static JsonObject getJsonRules(List<RuleDto> rules) {
    JsonObject jsonObject = new JsonObject();
    for (RuleDto rule : rules) {
      if (rule.isActive()) {
        jsonObject.addProperty(rule.name(), rule.value());
      }
    }
    return jsonObject;
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
