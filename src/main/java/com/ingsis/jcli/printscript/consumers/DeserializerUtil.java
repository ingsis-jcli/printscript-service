package com.ingsis.jcli.printscript.consumers;

import com.google.gson.Gson;
import com.ingsis.jcli.printscript.common.responses.TestCaseProduct;

public class DeserializerUtil {
  public static TestCaseProduct deserializeIntoTestCase(String json) {
    Gson gson = new Gson();
    TestCaseProduct testCase = gson.fromJson(json, TestCaseProduct.class);
    return testCase;
  }
}
