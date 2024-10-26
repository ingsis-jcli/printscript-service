package com.ingsis.jcli.printscript.utils;

import edu.InputProvider;
import edu.PrintEmitter;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TestInputProvider implements InputProvider {
  private final PrintEmitter printEmitter;
  private final Queue<String> inputs;

  public TestInputProvider(PrintEmitter printEmitter, List<String> inputs) {
    this.printEmitter = printEmitter;
    this.inputs = new LinkedList<>(inputs);
  }

  @Override
  public String input(String message) {
    // TODO: check if the message should be printed in test cases
    return inputs.poll();
  }
}
