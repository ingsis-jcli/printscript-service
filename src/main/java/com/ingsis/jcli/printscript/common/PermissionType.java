package com.ingsis.jcli.printscript.common;

public enum PermissionType {
  EXECUTE("execute"),
  FORMAT("edit"),
  ANALYZE("analyze");

  public final String name;

  PermissionType(String name) {
    this.name = name;
  }
}
