package com.ingsis.jcli.printscript.common;

import edu.InputProvider;
import edu.PrintEmitter;

public class UiInputProvider implements InputProvider {
  private final PrintEmitter printEmitter;

  public UiInputProvider(PrintEmitter printEmitter) {
    this.printEmitter = printEmitter;
  }

  @Override
  public String input(String s) {
    printEmitter.print(s);
    // TODO
    // ASK UI FOR INPUT
    return "";
  }
}
