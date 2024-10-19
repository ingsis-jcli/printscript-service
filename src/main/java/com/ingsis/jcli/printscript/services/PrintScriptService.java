package com.ingsis.jcli.printscript.services;

import static com.ingsis.jcli.printscript.common.PrintScriptUtil.getInputStreamFromString;
import static com.ingsis.jcli.printscript.common.PrintScriptUtil.getJsonRules;
import static com.ingsis.jcli.printscript.common.PrintScriptUtil.reportToString;

import com.google.gson.JsonObject;
import com.ingsis.jcli.printscript.common.ConsoleResult;
import com.ingsis.jcli.printscript.common.PrintAccumulator;
import com.ingsis.jcli.printscript.common.UiInputProvider;
import com.ingsis.jcli.printscript.common.responses.ValidateResponse;
import edu.FormatterResult;
import edu.Report;
import edu.Runner;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.stereotype.Service;

@Slf4j
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

  public ValidateResponse validate(String snippet) {
    Marker marker = MarkerFactory.getMarker("Validate");
    log.info(marker, "Validating snippet: " + snippet);

    InputStream code = getInputStreamFromString(snippet);
    ConsoleResult consoleResult = new ConsoleResult();

    try {
      runner.validate(code);
      log.info(marker, "Validated snippet: " + snippet);

      if (consoleResult.getResult() == null || consoleResult.getResult().isBlank()) {
        return new ValidateResponse();
      }
      return new ValidateResponse(consoleResult.getResult());
    } catch (Exception e) {
      log.error(marker, "Error validating snippet: " + e.getMessage());
      consoleResult.append(e.getMessage());
      return new ValidateResponse(consoleResult.getResult());
    }
  }
}
