package com.ingsis.jcli.printscript.producers;

import com.google.gson.JsonObject;
import com.ingsis.jcli.printscript.common.responses.ProcessStatus;
import com.ingsis.jcli.printscript.common.responses.TestType;

public class SerializerUtil {

  public static String serializeIntoStatusUpdate(
      Long snippetId, String operation, ProcessStatus status) {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("snippetId", snippetId);
    jsonObject.addProperty("operation", operation);
    jsonObject.addProperty("status", status.name());
    return jsonObject.toString();
  }

  public static String serializeIntoTestCase(Long testCaseId, TestType type) {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("testCaseId", testCaseId);
    jsonObject.addProperty("type", type.name());
    return jsonObject.toString();
  }
}
