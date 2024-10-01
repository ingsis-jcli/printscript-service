package com.ingsis.jcli.printscript.dto;

public class AnalyzerResponse {
  private Long snippetId;
  private String report;

  public AnalyzerResponse(Long snippetId, String report) {
    this.snippetId = snippetId;
    this.report = report;
  }

  public Long getSnippetId() {
    return snippetId;
  }

  public void setSnippetId(Long snippetId) {
    this.snippetId = snippetId;
  }

  public String getReport() {
    return report;
  }

  public void setReport(String report) {
    this.report = report;
  }
}
