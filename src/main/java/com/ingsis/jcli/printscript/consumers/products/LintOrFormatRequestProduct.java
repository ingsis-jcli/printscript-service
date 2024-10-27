package com.ingsis.jcli.printscript.consumers.products;

import com.ingsis.jcli.printscript.common.requests.RuleDto;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LintOrFormatRequestProduct {
  private Long snippetId;
  private String name;
  private String url;
  private String version;
  private List<RuleDto> rules;
}
