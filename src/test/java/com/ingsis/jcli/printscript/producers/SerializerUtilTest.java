package com.ingsis.jcli.printscript.producers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.gson.JsonObject;
import com.ingsis.jcli.printscript.common.responses.ProcessStatus;
import org.junit.jupiter.api.Test;

public class SerializerUtilTest {

  @Test
  void testSerializeIntoStatusUpdate() {
    Long snippetId = 123L;
    String operation = "format";
    ProcessStatus status = ProcessStatus.NON_COMPLIANT;

    JsonObject expectedJson = new JsonObject();
    expectedJson.addProperty("snippetId", snippetId);
    expectedJson.addProperty("operation", operation);
    expectedJson.addProperty("status", status.name());
    String expectedJsonString = expectedJson.toString();

    String resultJsonString =
        SerializerUtil.serializeIntoStatusUpdate(snippetId, operation, status);

    assertEquals(
        expectedJsonString,
        resultJsonString,
        "The serialized JSON string does not match the expected output.");
  }

  @Test
  void testSerializeWithDifferentValues() {
    Long snippetId = 456L;
    String operation = "analyze";
    ProcessStatus status = ProcessStatus.COMPLIANT;

    JsonObject expectedJson = new JsonObject();
    expectedJson.addProperty("snippetId", snippetId);
    expectedJson.addProperty("operation", operation);
    expectedJson.addProperty("status", status.name());
    String expectedJsonString = expectedJson.toString();

    String resultJsonString =
        SerializerUtil.serializeIntoStatusUpdate(snippetId, operation, status);

    assertEquals(
        expectedJsonString,
        resultJsonString,
        "The serialized JSON string does not match the expected output.");
  }

  @Test
  void testSerializeWithNullValues() {
    Long snippetId = null;
    String operation = null;
    ProcessStatus status = ProcessStatus.COMPLIANT;

    JsonObject expectedJson = new JsonObject();
    expectedJson.addProperty("snippetId", (Long) null);
    expectedJson.addProperty("operation", (String) null);
    expectedJson.addProperty("status", status.name());
    String expectedJsonString = expectedJson.toString();

    String resultJsonString =
        SerializerUtil.serializeIntoStatusUpdate(snippetId, operation, status);

    assertEquals(
        expectedJsonString,
        resultJsonString,
        "The serialized JSON string does not match the expected output.");
  }
}
