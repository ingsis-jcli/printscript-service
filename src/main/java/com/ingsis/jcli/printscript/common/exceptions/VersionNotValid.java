package com.ingsis.jcli.printscript.common.exceptions;

public class VersionNotValid extends RuntimeException {
  public VersionNotValid(String version) {
    super("Version: " + version + " is not valid!");
  }
}
