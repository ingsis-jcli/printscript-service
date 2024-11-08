package com.ingsis.jcli.printscript.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

import edu.InputProvider;
import edu.PrintEmitter;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestInputProviderTest {

  private PrintEmitter printEmitter;
  private InputProvider inputProvider;
  private List<String> sampleInputs;

  @BeforeEach
  void setUp() {
    printEmitter = mock(PrintEmitter.class);
    sampleInputs = List.of("Input 1", "Input 2", "Input 3");
    inputProvider = new TestInputProvider(printEmitter, sampleInputs);
  }

  @Test
  void testInputProviderInitialization() {
    assertNotNull(inputProvider, "InputProvider should be instantiated correctly.");
  }

  @Test
  void testInputReturnsValuesInOrder() {
    assertEquals("Input 1", inputProvider.input("Prompt 1"));
    assertEquals("Input 2", inputProvider.input("Prompt 2"));
    assertEquals("Input 3", inputProvider.input("Prompt 3"));
  }

  @Test
  void testInputReturnsNullWhenEmpty() {
    inputProvider.input("Prompt 1");
    inputProvider.input("Prompt 2");
    inputProvider.input("Prompt 3");

    assertNull(inputProvider.input("Prompt 4"), "Expected null when no more inputs are available.");
  }

  @Test
  void testMultipleCallsToInput() {
    String firstPrompt = "First Prompt";
    String secondPrompt = "Second Prompt";

    assertEquals("Input 1", inputProvider.input(firstPrompt));
    assertEquals("Input 2", inputProvider.input(secondPrompt));
  }
}
