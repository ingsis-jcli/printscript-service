package com.ingsis.jcli.printscript.common;

public class TestCaseData {
  private final String name;
  private final boolean valid;
  private final String url;

  public TestCaseData(String name, boolean valid, String url) {
    this.url = url;
    this.name = name;
    this.valid = valid;
  }

  public String getName() {
    return name;
  }

  public boolean isValid() {
    return valid;
  }

  public String getUrl() {
    return url;
  }
}
