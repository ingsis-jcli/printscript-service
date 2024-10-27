package com.ingsis.jcli.printscript.consumers.products;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TestCaseProduct {
  private Long id;
  private String snippetName;
  private String url;
  private List<String> input;
  private List<String> output;
  private String version;
}
