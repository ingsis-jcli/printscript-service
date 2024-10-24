package com.ingsis.jcli.printscript.controllers;

import com.ingsis.jcli.printscript.common.requests.AnalyzeRequest;
import com.ingsis.jcli.printscript.common.requests.ExecuteRequest;
import com.ingsis.jcli.printscript.common.requests.FormatRequest;
import com.ingsis.jcli.printscript.common.requests.ValidateRequest;
import com.ingsis.jcli.printscript.common.responses.ErrorResponse;
import com.ingsis.jcli.printscript.services.PrintScriptService;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/printscript")
public class PrintScriptController {

  final PrintScriptService printScriptService;

  @Autowired
  public PrintScriptController(PrintScriptService printScriptService) {
    this.printScriptService = printScriptService;
  }

  @PostMapping(value = "/validate", consumes = "application/json")
  public ResponseEntity<ErrorResponse> validate(@RequestBody ValidateRequest validateRequest) {
    Marker marker = MarkerFactory.getMarker("Validate");
    log.info(marker, "ValidateRequest received: " + validateRequest);

    ErrorResponse response =
        printScriptService.validate(validateRequest.snippet(), validateRequest.version());
    log.info(marker, "printscript response: \"" + response + "\"");

    if (Objects.equals(response.error(), "")) {
      return new ResponseEntity<>(HttpStatus.OK);
    }
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // TODO: custom status code?
  }

  @PostMapping("/format")
  public ResponseEntity<String> format(@RequestBody FormatRequest formatRequest) {
    String formattedSnippet =
        printScriptService.format(
            formatRequest.snippet(), formatRequest.config(), formatRequest.version());
    return new ResponseEntity<>(formattedSnippet, HttpStatus.OK);
  }

  @PostMapping("/analyze")
  public ResponseEntity<String> analyze(@RequestBody AnalyzeRequest analyzeRequest) {
    String report =
        printScriptService.analyze(
            analyzeRequest.snippet(), analyzeRequest.config(), analyzeRequest.version());
    return new ResponseEntity<>(report, HttpStatus.OK);
  }

  @PostMapping("/execute")
  public ResponseEntity<String> execute(@RequestBody ExecuteRequest executeRequest) {
    String result = printScriptService.execute(executeRequest.snippet(), executeRequest.version());
    return new ResponseEntity<>(result, HttpStatus.OK);
  }
}
