package com.ingsis.jcli.printscript.common;

public enum OperationType {
  EXECUTE("interpreter"),
  FORMAT("formatter"),
  ANALYZE("analyzer"),
  VALIDATE("validator"),
  TEST("testCases");

  public final String name;

  OperationType(String name) {
    this.name = name;
  }
}
