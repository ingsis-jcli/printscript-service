package com.ingsis.jcli.printscript.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PrintAccumulatorTest {

  private ConsoleResult mockConsoleResult;
  private PrintAccumulator printAccumulator;

  @BeforeEach
  void setUp() {
    mockConsoleResult = mock(ConsoleResult.class);
    printAccumulator = new PrintAccumulator(mockConsoleResult);
  }

  @Test
  void testPrint_AddsToPrintsList() {
    String message = "Hello, world!";

    printAccumulator.print(message);

    List<String> prints = printAccumulator.getPrints();
    assertEquals(1, prints.size(), "Prints list should contain one item.");
    assertEquals(message, prints.get(0), "Prints list should contain the printed message.");
  }

  @Test
  void testPrint_AppendsToConsoleResult() {
    String message = "This is a test.";

    printAccumulator.print(message);

    verify(mockConsoleResult, times(1)).append(message);
  }

  @Test
  void testMultiplePrints() {
    String message1 = "First message";
    String message2 = "Second message";

    printAccumulator.print(message1);
    printAccumulator.print(message2);

    List<String> prints = printAccumulator.getPrints();
    assertEquals(2, prints.size(), "Prints list should contain two items.");
    assertEquals(message1, prints.get(0), "First message should be in the prints list.");
    assertEquals(message2, prints.get(1), "Second message should be in the prints list.");

    verify(mockConsoleResult, times(1)).append(message1);
    verify(mockConsoleResult, times(1)).append(message2);
  }

  @Test
  void testNoPrintsInitially() {
    List<String> prints = printAccumulator.getPrints();
    assertTrue(prints.isEmpty(), "Prints list should be empty initially.");
  }
}
