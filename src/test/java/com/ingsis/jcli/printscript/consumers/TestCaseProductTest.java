package com.ingsis.jcli.printscript.consumers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ingsis.jcli.printscript.consumers.products.TestCaseProduct;
import java.util.List;
import org.junit.jupiter.api.Test;

class TestCaseProductTest {

  @Test
  void testGetters() {
    Long id = 1L;
    String snippetName = "Test Snippet";
    String url = "http://example.com/snippet";
    String version = "1.0";

    List<String> input = List.of("input1", "input2");
    List<String> output = List.of("output1", "output2");

    TestCaseProduct product = new TestCaseProduct();
    product.setId(id);
    product.setSnippetName(snippetName);
    product.setUrl(url);
    product.setInput(input);
    product.setOutput(output);
    product.setVersion(version);

    assertEquals(id, product.getId());
    assertEquals(snippetName, product.getSnippetName());
    assertEquals(url, product.getUrl());
    assertEquals(version, product.getVersion());
    assertEquals(input, product.getInput());
    assertEquals(output, product.getOutput());
    assertNotNull(product.getInput());
    assertNotNull(product.getOutput());
  }
}
