package com.ingsis.jcli.printscript.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConsoleResultTest {

  private ConsoleResult consoleResult;

  @BeforeEach
  void setUp() {
    consoleResult = new ConsoleResult();
  }

  @Test
  void testAppend_SingleMessage() {
    String message = "Hello, world!";

    consoleResult.append(message);
    String result = consoleResult.getResult();

    assertNotNull(result, "Result should not be null.");
    assertTrue(result.contains(message), "Result should contain the appended message.");
    assertEquals(
        message + System.lineSeparator(),
        result,
        "Result should include the message and a newline.");
  }

  @Test
  void testAppend_MultipleMessages() {
    String message1 = "First message";
    String message2 = "Second message";

    consoleResult.append(message1);
    consoleResult.append(message2);
    String result = consoleResult.getResult();

    assertNotNull(result, "Result should not be null.");
    assertTrue(result.contains(message1), "Result should contain the first appended message.");
    assertTrue(result.contains(message2), "Result should contain the second appended message.");
    assertEquals(
        message1 + System.lineSeparator() + message2 + System.lineSeparator(),
        result,
        "Result should include all messages with newlines.");
  }

  @Test
  void testGetResult_Empty() {
    String result = consoleResult.getResult();
    assertEquals("", result, "Result should be an empty string initially.");
  }
}
