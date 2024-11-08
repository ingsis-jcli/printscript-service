package com.ingsis.jcli.printscript.utils;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.gson.JsonObject;
import com.ingsis.jcli.printscript.common.requests.RuleDto;
import edu.Report;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class TestPrintScriptUtil {

  @Test
  void testGetInputStreamFromString() throws IOException {
    String snippet = "print('Hello, World!');";
    InputStream inputStream = PrintScriptUtil.getInputStreamFromString(snippet);

    byte[] actualBytes = inputStream.readAllBytes();
    assertArrayEquals(
        snippet.getBytes(), actualBytes, "The byte array should match the original string's bytes");
  }

  @Test
  void testGetJsonRules() {
    RuleDto activeRule1 = new RuleDto(true, "rule1", "value1");
    RuleDto inactiveRule = new RuleDto(false, "rule2", "value2");
    RuleDto activeRule2 = new RuleDto(true, "rule3", "value3");
    List<RuleDto> rules = Arrays.asList(activeRule1, inactiveRule, activeRule2);

    JsonObject jsonRules = PrintScriptUtil.getJsonLintingRules(rules);

    assertTrue(jsonRules.has("rule1"), "JSON should have active rule1");
    assertTrue(jsonRules.has("rule3"), "JSON should have active rule3");
    assertFalse(jsonRules.has("rule2"), "JSON should not have inactive rule2");

    assertEquals(
        "value1", jsonRules.get("rule1").getAsString(), "rule1 should have the correct value");
    assertEquals(
        "value3", jsonRules.get("rule3").getAsString(), "rule3 should have the correct value");
  }

  @Test
  void testReportToString_WithMessages() {
    Report mockReport = mock(Report.class);
    List<String> messages = Arrays.asList("Message 1", "Message 2", "Message 3");
    when(mockReport.getReport()).thenReturn(Optional.of(messages));

    String reportString = PrintScriptUtil.reportToString(mockReport);

    String expectedString = String.join("\n", messages);
    assertEquals(
        expectedString,
        reportString,
        "The report string should match the expected joined messages");
  }

  @Test
  void testReportToString_WithoutMessages() {
    Report mockReport = mock(Report.class);
    when(mockReport.getReport()).thenReturn(Optional.empty());

    String reportString = PrintScriptUtil.reportToString(mockReport);

    assertEquals(
        "", reportString, "The report string should be empty when no messages are present");
  }

  @Test
  void testReportToString_WithEmptyMessages() {
    Report mockReport = mock(Report.class);
    when(mockReport.getReport()).thenReturn(Optional.of(Collections.emptyList()));

    String reportString = PrintScriptUtil.reportToString(mockReport);

    assertEquals("", reportString, "The report string should be empty when messages list is empty");
  }
}
