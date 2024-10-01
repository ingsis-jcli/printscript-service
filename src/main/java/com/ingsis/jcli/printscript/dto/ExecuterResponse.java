package com.ingsis.jcli.printscript.dto;

public class ExecuterResponse {
  private Long snippetId;
  private String output;

  public ExecuterResponse(Long snippetId, String output) {
    this.snippetId = snippetId;
    this.output = output;
  }

  public Long getSnippetId() {
    return snippetId;
  }

  public void setSnippetId(Long snippetId) {
    this.snippetId = snippetId;
  }

  public String getOutput() {
    return output;
  }

  public void setOutput(String output) {
    this.output = output;
  }
}
