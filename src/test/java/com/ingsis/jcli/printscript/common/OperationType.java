package com.ingsis.jcli.printscript.common;

public enum OperationType {
  EXECUTE("execute"),
  FORMAT("formatter"),
  ANALYZE("analyzer");

  public final String name;

  OperationType(String name) {
    this.name = name;
  }
}
