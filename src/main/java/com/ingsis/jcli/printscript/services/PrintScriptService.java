package com.ingsis.jcli.printscript.services;

import static com.ingsis.jcli.printscript.common.PrintScriptUtil.getInputStreamFromString;
import static com.ingsis.jcli.printscript.common.PrintScriptUtil.getJsonRules;
import static com.ingsis.jcli.printscript.common.PrintScriptUtil.reportToString;

import com.google.gson.JsonObject;
import com.ingsis.jcli.printscript.common.ConsoleResult;
import com.ingsis.jcli.printscript.common.PrintAccumulator;
import com.ingsis.jcli.printscript.common.UiInputProvider;
import edu.FormatterResult;
import edu.Report;
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

  public String analyze(String snippet, String config) {
    InputStream code = getInputStreamFromString(snippet);
    JsonObject rules = getJsonRules(config);
    Report result = runner.analyze(code, rules);
    return reportToString(result);
  }

  public String execute(String snippet) {
    InputStream code = getInputStreamFromString(snippet);
    ConsoleResult consoleResult = new ConsoleResult();
    PrintAccumulator printAccumulator = new PrintAccumulator(consoleResult);
    UiInputProvider uiInputProvider = new UiInputProvider(printAccumulator);
    try {
      runner.execute(code, uiInputProvider, printAccumulator);
      return consoleResult.getResult();
    } catch (Exception e) {
      consoleResult.append(e.getMessage());
      return consoleResult.getResult();
    }
  }

  public String validate(String snippet) {
    InputStream code = getInputStreamFromString(snippet);
    ConsoleResult consoleResult = new ConsoleResult();
    try {
      runner.validate(code);
      return consoleResult.getResult();
    } catch (Exception e) {
      consoleResult.append(e.getMessage());
      return consoleResult.getResult();
    }
  }
}
