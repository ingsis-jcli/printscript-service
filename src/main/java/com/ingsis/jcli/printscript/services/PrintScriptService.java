package com.ingsis.jcli.printscript.services;

import static com.ingsis.jcli.printscript.common.PrintScriptUtil.getInputStreamFromString;
import static com.ingsis.jcli.printscript.common.PrintScriptUtil.getJsonRules;

import com.google.gson.JsonObject;
import edu.FormatterResult;
import edu.Runner;
import java.io.InputStream;
import org.springframework.stereotype.Service;

@Service
public class PrintScriptService {
  private final Runner runner;

  public PrintScriptService() {
    this.runner = new Runner("1.1");
  }

  public String format(String snippet, String config) {
    InputStream code = getInputStreamFromString(snippet);
    JsonObject rules = getJsonRules(config);
    FormatterResult result = runner.format(code, rules);
    return result.getResult();
  }
}
