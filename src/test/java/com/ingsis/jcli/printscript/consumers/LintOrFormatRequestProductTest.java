package com.ingsis.jcli.printscript.consumers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ingsis.jcli.printscript.common.requests.RuleDto;
import com.ingsis.jcli.printscript.consumers.products.LintOrFormatRequestProduct;
import java.util.List;
import org.junit.jupiter.api.Test;

class LintOrFormatRequestProductTest {

  @Test
  void testGetters() {
    Long snippetId = 1L;
    String name = "Test Snippet";
    String url = "http://example.com/snippet";
    String version = "1.0";

    RuleDto rule1 = new RuleDto(true, "NoConsoleLog", "noConsole");
    RuleDto rule2 = new RuleDto(false, "MaxLineLength", "80");
    List<RuleDto> rules = List.of(rule1, rule2);

    LintOrFormatRequestProduct product = new LintOrFormatRequestProduct();
    product.setName(name);
    product.setSnippetId(snippetId);
    product.setUrl(url);
    product.setVersion(version);
    product.setRules(rules);

    assertEquals(snippetId, product.getSnippetId());
    assertEquals(name, product.getName());
    assertEquals(url, product.getUrl());
    assertEquals(version, product.getVersion());
    assertEquals(rules, product.getRules());
    assertNotNull(product.getRules());
  }
}
