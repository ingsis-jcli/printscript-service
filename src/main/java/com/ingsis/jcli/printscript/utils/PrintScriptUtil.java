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

  public static JsonObject getJsonLintingRules(List<RuleDto> rules) {
    JsonObject jsonObject = new JsonObject();

    for (RuleDto rule : rules) {
      if (rule.isActive()) {
        if (rule.value() != null) {
          jsonObject.addProperty(rule.name(), rule.value());
        } else {
          jsonObject.addProperty(rule.name(), true);
        }
      }
    }
    return jsonObject;
  }

  public static JsonObject getJsonFormattingRules(List<RuleDto> rules) {
    JsonObject jsonObject = new JsonObject();
    for (RuleDto rule : rules) {
      if (rule.isActive()) {
        if (rule.value() != null) {
          jsonObject.addProperty(rule.name(), Integer.parseInt(rule.value()));
        } else {
          jsonObject.addProperty(rule.name(), true);
        }
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
