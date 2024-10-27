package com.ingsis.jcli.printscript.consumers;

import static org.assertj.core.api.Assertions.assertThat;

import com.ingsis.jcli.printscript.common.requests.RuleDto;
import com.ingsis.jcli.printscript.consumers.products.LintOrFormatRequestProduct;
import com.ingsis.jcli.printscript.consumers.products.TestCaseProduct;
import org.junit.jupiter.api.Test;

public class DeserializerUtilTest {

  @Test
  void testDeserializeIntoTestCase() {
    String json =
        """
        {
          "id": 1,
          "snippetName": "Test Snippet",
          "url": "http://example.com",
          "input": ["input1", "input2"],
          "output": ["output1", "output2"],
          "version": "1.1"
        }
        """;

    TestCaseProduct product = DeserializerUtil.deserializeIntoTestCase(json);

    assertThat(product).isNotNull();
    assertThat(product.getId()).isEqualTo(1L);
    assertThat(product.getSnippetName()).isEqualTo("Test Snippet");
    assertThat(product.getUrl()).isEqualTo("http://example.com");
    assertThat(product.getInput()).containsExactly("input1", "input2");
    assertThat(product.getOutput()).containsExactly("output1", "output2");
    assertThat(product.getVersion()).isEqualTo("1.1");
  }

  @Test
  void testDeserializeIntoRequestProduct() {
    String json =
        """
        {
          "snippetId": 123,
          "name": "Test Snippet",
          "url": "http://example.com",
          "version": "1.1",
          "rules": [
            {
              "isActive": true,
              "name": "rule1",
              "value": "value1"
            },
            {
              "isActive": false,
              "name": "rule2",
              "value": "value2"
            }
          ]
        }
        """;

    LintOrFormatRequestProduct product = DeserializerUtil.deserializeIntoRequestProduct(json);

    assertThat(product).isNotNull();
    assertThat(product.getSnippetId()).isEqualTo(123L);
    assertThat(product.getName()).isEqualTo("Test Snippet");
    assertThat(product.getUrl()).isEqualTo("http://example.com");
    assertThat(product.getVersion()).isEqualTo("1.1");
    assertThat(product.getRules()).hasSize(2);

    RuleDto firstRule = product.getRules().get(0);
    assertThat(firstRule.isActive()).isTrue();
    assertThat(firstRule.name()).isEqualTo("rule1");
    assertThat(firstRule.value()).isEqualTo("value1");

    RuleDto secondRule = product.getRules().get(1);
    assertThat(secondRule.isActive()).isFalse();
    assertThat(secondRule.name()).isEqualTo("rule2");
    assertThat(secondRule.value()).isEqualTo("value2");
  }
}
