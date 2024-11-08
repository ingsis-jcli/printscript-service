package com.ingsis.jcli.printscript.consumers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ingsis.jcli.printscript.common.requests.RuleDto;
import com.ingsis.jcli.printscript.consumers.products.LintOrFormatRequestProduct;
import com.ingsis.jcli.printscript.consumers.products.RuleDtoAdapter;
import com.ingsis.jcli.printscript.consumers.products.TestCaseProduct;

public class DeserializerUtil {
  public static TestCaseProduct deserializeIntoTestCase(String json) {
    Gson gson = new Gson();
    TestCaseProduct testCase = gson.fromJson(json, TestCaseProduct.class);
    return testCase;
  }

  public static LintOrFormatRequestProduct deserializeIntoRequestProduct(String json) {
    Gson gson = new GsonBuilder().registerTypeAdapter(RuleDto.class, new RuleDtoAdapter()).create();
    LintOrFormatRequestProduct testCase = gson.fromJson(json, LintOrFormatRequestProduct.class);
    return testCase;
  }
}
