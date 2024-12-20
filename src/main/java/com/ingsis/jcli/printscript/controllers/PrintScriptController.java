package com.ingsis.jcli.printscript.controllers;

import com.ingsis.jcli.printscript.common.requests.AnalyzeRequest;
import com.ingsis.jcli.printscript.common.requests.ExecuteRequest;
import com.ingsis.jcli.printscript.common.requests.FormatRequest;
import com.ingsis.jcli.printscript.common.requests.RuleDto;
import com.ingsis.jcli.printscript.common.requests.TestCaseRequest;
import com.ingsis.jcli.printscript.common.requests.ValidateRequest;
import com.ingsis.jcli.printscript.common.responses.ErrorResponse;
import com.ingsis.jcli.printscript.common.responses.FormatResponse;
import com.ingsis.jcli.printscript.common.responses.TestType;
import com.ingsis.jcli.printscript.services.PrintScriptService;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping()
public class PrintScriptController {

  final PrintScriptService printScriptService;

  @Autowired
  public PrintScriptController(PrintScriptService printScriptService) {
    this.printScriptService = printScriptService;
  }

  @PostMapping(
      value = "/validate",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ErrorResponse> validate(@RequestBody ValidateRequest validateRequest) {
    Marker marker = MarkerFactory.getMarker("Validate");
    log.info(marker, "ValidateRequest received: " + validateRequest);

    ErrorResponse response =
        printScriptService.validate(
            validateRequest.name(), validateRequest.url(), validateRequest.version());

    log.info(marker, "printscript response: \"" + response + "\"");

    if (Objects.equals(response.error(), "")) {
      return new ResponseEntity<>(new ErrorResponse(""), HttpStatus.OK);
    }
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @PostMapping("/format")
  public ResponseEntity<FormatResponse> format(@RequestBody FormatRequest formatRequest) {
    FormatResponse formattedSnippet =
        printScriptService.format(
            formatRequest.name(),
            formatRequest.url(),
            formatRequest.rules(),
            formatRequest.version());
    return new ResponseEntity<>(formattedSnippet, HttpStatus.OK);
  }

  @PostMapping("/analyze")
  public ResponseEntity<ErrorResponse> analyze(@RequestBody AnalyzeRequest analyzeRequest) {
    ErrorResponse report =
        printScriptService.analyze(
            analyzeRequest.name(),
            analyzeRequest.url(),
            analyzeRequest.rules(),
            analyzeRequest.version());
    return new ResponseEntity<>(report, HttpStatus.OK);
  }

  @PostMapping("/execute")
  public ResponseEntity<String> execute(@RequestBody ExecuteRequest executeRequest) {
    String result =
        printScriptService.execute(
            executeRequest.name(), executeRequest.url(), executeRequest.version());
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @GetMapping("/formatting_rules")
  public ResponseEntity<List<RuleDto>> getFormattingRules(@RequestParam String version) {
    Marker marker = MarkerFactory.getMarker("FormattingRules");
    log.info(marker, "Fetching default formatting rules for version: " + version);

    List<RuleDto> rules = printScriptService.getDefaultFormattingRules(version);
    return new ResponseEntity<>(rules, HttpStatus.OK);
  }

  @GetMapping("/linting_rules")
  public ResponseEntity<List<RuleDto>> getLintingRules(@RequestParam String version) {
    Marker marker = MarkerFactory.getMarker("LintingRules");
    log.info(marker, "Fetching default linting rules for version: " + version);

    List<RuleDto> rules = printScriptService.getDefaultLintingRules(version);
    log.info(marker, "Rules: " + rules);
    return new ResponseEntity<>(rules, HttpStatus.OK);
  }

  @PostMapping(
      value = "/test",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TestType> runTestCase(@RequestBody TestCaseRequest testCaseRequest) {
    TestType testType =
        printScriptService.runTestCase(
            testCaseRequest.snippetName(),
            testCaseRequest.url(),
            testCaseRequest.input(),
            testCaseRequest.output(),
            testCaseRequest.version());
    return new ResponseEntity<>(testType, HttpStatus.OK);
  }
}
