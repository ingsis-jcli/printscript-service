package com.ingsis.jcli.printscript.common;

import edu.PrintEmitter;

public class PrintAccumulator implements PrintEmitter {
  private final ConsoleResult consoleResult;

  public PrintAccumulator(ConsoleResult consoleResult) {
    this.consoleResult = consoleResult;
  }

  @Override
  public void print(String s) {
    consoleResult.append(s);
  }
}
