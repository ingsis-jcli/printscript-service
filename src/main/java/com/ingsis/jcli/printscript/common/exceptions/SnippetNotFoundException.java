package com.ingsis.jcli.printscript.common.exceptions;

public class SnippetNotFoundException extends RuntimeException {
  public SnippetNotFoundException(String name, String container) {
    super("Snippet names: " + name + " at " + container + " not found!");
  }
}
