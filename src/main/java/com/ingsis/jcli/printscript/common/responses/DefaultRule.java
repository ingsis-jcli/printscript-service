package com.ingsis.jcli.printscript.common.responses;

import lombok.Data;

@Data
public class DefaultRule {
  private String name;

  private boolean isActive;

  private String value;

  public DefaultRule(String name, boolean isActive, String value) {
    this.name = name;
    this.isActive = isActive;
    this.value = value;
  }
}
