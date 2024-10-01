package com.ingsis.jcli.printscript.common;

public enum OperationType {
  EXECUTE("execute"),
  FORMAT("formatter"),
  ANALYZE("analyze");

  public final String name;

  OperationType(String name) {
    this.name = name;
  }
}
