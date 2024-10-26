package com.ingsis.jcli.printscript.common.responses;

import java.util.List;
import lombok.Getter;

@Getter
public class TestCaseProduct {
  private Long id;
  private String snippetName;
  private String url;
  private List<String> input;
  private List<String> output;
}
