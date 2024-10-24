package com.ingsis.jcli.printscript.services;

import static com.ingsis.jcli.printscript.common.PrintScriptUtil.getInputStreamFromString;
import static com.ingsis.jcli.printscript.common.PrintScriptUtil.getJsonRules;
import static com.ingsis.jcli.printscript.common.PrintScriptUtil.reportToString;

import com.google.gson.JsonObject;
import com.ingsis.jcli.printscript.common.ConsoleResult;
import com.ingsis.jcli.printscript.common.PrintAccumulator;
import com.ingsis.jcli.printscript.common.UiInputProvider;
import com.ingsis.jcli.printscript.common.responses.DefaultRule;
import com.ingsis.jcli.printscript.common.responses.ErrorResponse;
import edu.FormatterResult;
import edu.Report;
import edu.Runner;
import edu.utils.DefaultRulesFactory;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PrintScriptService {
  private final List<String> availableVersions;

  public PrintScriptService() {
    this.availableVersions = List.of("1.0", "1.1");
  }

  public String format(String snippet, String config, String version) {
    InputStream code = getInputStreamFromString(snippet);
    JsonObject rules = getJsonRules(config);
    if (!availableVersions.contains(version)) {
      throw new IllegalArgumentException("Invalid version: " + version);
    }
    Runner runner = new Runner(version);
    FormatterResult result = runner.format(code, rules);
    return result.getResult();
  }

  public String analyze(String snippet, String config, String version) {
    InputStream code = getInputStreamFromString(snippet);
    JsonObject rules = getJsonRules(config);
    if (!availableVersions.contains(version)) {
      throw new IllegalArgumentException("Invalid version: " + version);
    }
    Runner runner = new Runner(version);
    Report result = runner.analyze(code, rules);
    return reportToString(result);
  }

  public String execute(String snippet, String version) {
    InputStream code = getInputStreamFromString(snippet);
    ConsoleResult consoleResult = new ConsoleResult();
    PrintAccumulator printAccumulator = new PrintAccumulator(consoleResult);
    UiInputProvider uiInputProvider = new UiInputProvider(printAccumulator);
    if (!availableVersions.contains(version)) {
      throw new IllegalArgumentException("Invalid version: " + version);
    }
    Runner runner = new Runner(version);
    try {
      runner.execute(code, uiInputProvider, printAccumulator);
      return consoleResult.getResult();
    } catch (Exception e) {
      consoleResult.append(e.getMessage());
      return consoleResult.getResult();
    }
  }

  public ErrorResponse validate(String snippet, String version) {
    Marker marker = MarkerFactory.getMarker("Validate");
    log.info(marker, "Validating snippet: " + snippet);

    InputStream code = getInputStreamFromString(snippet);
    ConsoleResult consoleResult = new ConsoleResult();

    if (!availableVersions.contains(version)) {
      log.error(marker, "Invalid version: " + version);
      consoleResult.append("Invalid version: " + version);
      return new ErrorResponse(consoleResult.getResult());
    }

    Runner runner = new Runner(version);

    try {
      runner.validate(code);
      log.info(marker, "Validated snippet: " + snippet);

      if (consoleResult.getResult() == null || consoleResult.getResult().isBlank()) {
        return new ErrorResponse("");
      }
      return new ErrorResponse(consoleResult.getResult());
    } catch (Exception e) {
      log.error(marker, "Error validating snippet: " + e.getMessage());
      consoleResult.append(e.getMessage());
      return new ErrorResponse(consoleResult.getResult());
    }
  }

  public List<DefaultRule> getDefaultFormattingRules(String version) {
    DefaultRulesFactory rulesFactory = new DefaultRulesFactory(version);
    var defaultFormattingRules = rulesFactory.getDefaultFormattingRules();

    List<DefaultRule> rules =
        defaultFormattingRules.entrySet().stream()
            .map(rule -> new DefaultRule(rule.getKey(), true, rule.getValue().getAsString()))
            .collect(Collectors.toList());

    return rules;
  }

  public List<DefaultRule> getDefaultLintingRules(String version) {
    DefaultRulesFactory rulesFactory = new DefaultRulesFactory(version);
    var defaultLintingRules = rulesFactory.getDefaultLintingRules();

    List<DefaultRule> rules =
        defaultLintingRules.entrySet().stream()
            .map(entry -> new DefaultRule(entry.getKey(), true, entry.getValue().getAsString()))
            .collect(Collectors.toList());

    return rules;
  }
}
