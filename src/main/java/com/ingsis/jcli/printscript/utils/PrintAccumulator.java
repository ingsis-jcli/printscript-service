package com.ingsis.jcli.printscript.utils;

import edu.PrintEmitter;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

public class PrintAccumulator implements PrintEmitter {
  private final ConsoleResult consoleResult;
  @Getter private final List<String> prints;

  public PrintAccumulator(ConsoleResult consoleResult) {
    this.consoleResult = consoleResult;
    this.prints = new ArrayList<>();
  }

  @Override
  public void print(String s) {
    prints.add(s);
    consoleResult.append(s);
  }
}
