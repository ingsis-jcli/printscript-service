package com.ingsis.jcli.printscript.common;

public class TestCaseData {
  private final String name;
  private final boolean valid;

  public TestCaseData(String name, boolean valid) {
    this.name = name;
    this.valid = valid;
  }

  public String getName() {
    return name;
  }

  public boolean isValid() {
    return valid;
  }
}
