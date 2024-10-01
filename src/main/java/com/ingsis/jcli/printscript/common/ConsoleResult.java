package com.ingsis.jcli.printscript.common;

public class ConsoleResult {
  private StringBuilder result;

  public ConsoleResult() {
    this.result = new StringBuilder();
  }

  public void append(String s) {
    result.append(s).append(System.lineSeparator());
  }

  public String getResult() {
    return result.toString();
  }
}
