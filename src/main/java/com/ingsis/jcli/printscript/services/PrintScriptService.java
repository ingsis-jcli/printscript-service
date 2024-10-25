package com.ingsis.jcli.printscript.services;

import static com.ingsis.jcli.printscript.common.PrintScriptUtil.getJsonRules;
import static com.ingsis.jcli.printscript.common.PrintScriptUtil.reportToString;

import com.google.gson.JsonObject;
import com.ingsis.jcli.printscript.common.ConsoleResult;
import com.ingsis.jcli.printscript.common.PrintAccumulator;
import com.ingsis.jcli.printscript.common.UiInputProvider;
import com.ingsis.jcli.printscript.common.requests.RuleDto;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PrintScriptService {
  private final List<String> availableVersions;
  private final SnippetsService snippetsService;

  @Autowired
  public PrintScriptService(SnippetsService snippetsService) {
    this.availableVersions = List.of("1.0", "1.1");
    this.snippetsService = snippetsService;
  }

  public String format(String name, String url, List<RuleDto> config, String version) {
    InputStream code = snippetsService.getSnippetStream(name, url);
    JsonObject rules = getJsonRules(config);
    if (!availableVersions.contains(version)) {
      throw new IllegalArgumentException("Invalid version: " + version);
    }
    Runner runner = new Runner(version);
    FormatterResult result = runner.format(code, rules);
    return result.getResult();
  }

  public String analyze(String name, String url, List<RuleDto> rules, String version) {
    JsonObject rulesJson = getJsonRules(rules);
    InputStream code = snippetsService.getSnippetStream(name, url);
    if (!availableVersions.contains(version)) {
      throw new IllegalArgumentException("Invalid version: " + version);
    }
    Runner runner = new Runner(version);
    Report result = runner.analyze(code, rulesJson);
    return reportToString(result);
  }

  public String execute(String name, String url, String version) {
    InputStream code = snippetsService.getSnippetStream(name, url);
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

  public ErrorResponse validate(String name, String url, String version) {
    Marker marker = MarkerFactory.getMarker("Validate");
    log.info(marker, "Looking for snippet with name: " + name);
    log.info(marker, "Looking for snippet at container: " + url);
    InputStream code = snippetsService.getSnippetStream(name, url);
    ConsoleResult consoleResult = new ConsoleResult();

    if (!availableVersions.contains(version)) {
      log.error(marker, "Invalid version: " + version);
      consoleResult.append("Invalid version: " + version);
      return new ErrorResponse(consoleResult.getResult());
    }

    Runner runner = new Runner(version);

    try {
      runner.validate(code);
      log.info(marker, "Validated snippet: " + name);

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

  public List<RuleDto> getDefaultFormattingRules(String version) {
    DefaultRulesFactory rulesFactory = new DefaultRulesFactory(version);
    var defaultFormattingRules = rulesFactory.getDefaultFormattingRules();

    List<RuleDto> rules =
        defaultFormattingRules.entrySet().stream()
            .map(rule -> new RuleDto(false, rule.getKey(), rule.getValue().getAsString()))
            .collect(Collectors.toList());

    return rules;
  }

  public List<RuleDto> getDefaultLintingRules(String version) {
    DefaultRulesFactory rulesFactory = new DefaultRulesFactory(version);
    var defaultLintingRules = rulesFactory.getDefaultLintingRules();

    List<RuleDto> rules =
        defaultLintingRules.entrySet().stream()
            .map(entry -> new RuleDto(false, entry.getKey(), entry.getValue().getAsString()))
            .collect(Collectors.toList());

    return rules;
  }
}
