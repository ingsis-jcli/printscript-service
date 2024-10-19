package com.ingsis.jcli.printscript.controllers;

import com.ingsis.jcli.printscript.common.requests.ValidateRequest;
import com.ingsis.jcli.printscript.common.responses.ValidateResponse;
import com.ingsis.jcli.printscript.services.PrintScriptService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
  public ResponseEntity<ValidateResponse> validate(@RequestBody ValidateRequest validateRequest) {
    Marker marker = MarkerFactory.getMarker("Validate");
    log.info(marker, "ValidateRequest received: " + validateRequest);

    // TODO: use version
    ValidateResponse response = printScriptService.validate(validateRequest.snippet());
    log.info(marker, "printscript response: \"" + response + "\"");

    if (response.isValid()) {
      return new ResponseEntity<>(response, HttpStatus.OK);
    }
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // TODO: custom status code?
  }

  @PostMapping("/format")
  public ResponseEntity<String> format(@RequestParam String snippet, @RequestParam String config) {
    String formattedSnippet = printScriptService.format(snippet, config);
    return new ResponseEntity<>(formattedSnippet, HttpStatus.OK);
  }

  @PostMapping("/analyze")
  public ResponseEntity<String> analyze(@RequestParam String snippet, @RequestParam String config) {
    String report = printScriptService.analyze(snippet, config);
    return new ResponseEntity<>(report, HttpStatus.OK);
  }

  @PostMapping("/execute")
  public ResponseEntity<String> execute(@RequestParam String snippet) {
    String result = printScriptService.execute(snippet);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }
}
