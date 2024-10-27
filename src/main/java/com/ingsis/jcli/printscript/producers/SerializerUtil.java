package com.ingsis.jcli.printscript.producers;

import com.google.gson.JsonObject;
import com.ingsis.jcli.printscript.common.responses.ProcessStatus;

public class SerializerUtil {

  public static String serializeIntoStatusUpdate(
      Long snippetId, String operation, ProcessStatus status) {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("snippetId", snippetId);
    jsonObject.addProperty("operation", operation);
    jsonObject.addProperty("status", status.name());
    return jsonObject.toString();
  }
}
