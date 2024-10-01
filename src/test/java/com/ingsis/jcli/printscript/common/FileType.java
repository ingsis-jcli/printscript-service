package com.ingsis.jcli.printscript.common;

public enum FileType {
  INPUT("input"),
  OUTPUT("output"),
  RULES("rules");

  public final String name;

  FileType(String name) {
    this.name = name;
  }
}
