package com.ingsis.jcli.printscript.common;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class PrintScriptUtil {
  public static InputStream getInputStreamFromString(String snippet) {
    return new ByteArrayInputStream(snippet.getBytes());
  }

  public static JsonObject getJsonRules(String config) {
    return JsonParser.parseString(config).getAsJsonObject();
  }
}
