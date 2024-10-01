package com.ingsis.jcli.printscript.dto;

public class FormatterResponse {
  private Long snippetId;
  private String formattedSnippet;

  public FormatterResponse(Long snippetId, String formattedSnippet) {
    this.snippetId = snippetId;
    this.formattedSnippet = formattedSnippet;
  }

  public Long getSnippetId() {
    return snippetId;
  }

  public void setSnippetId(Long snippetId) {
    this.snippetId = snippetId;
  }

  public String getFormattedSnippet() {
    return formattedSnippet;
  }

  public void setFormattedSnippet(String formattedSnippet) {
    this.formattedSnippet = formattedSnippet;
  }
}
