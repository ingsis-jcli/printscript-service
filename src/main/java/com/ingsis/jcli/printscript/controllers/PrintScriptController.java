package com.ingsis.jcli.printscript.controllers;

import com.ingsis.jcli.printscript.common.requests.ValidateRequest;
import com.ingsis.jcli.printscript.services.PrintScriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/printscript")
public class PrintScriptController {

  final PrintScriptService printScriptService;

  @Autowired
  public PrintScriptController(PrintScriptService printScriptService) {
    this.printScriptService = printScriptService;
  }

  @PostMapping(value = "/validate", consumes = "application/json")
  public ResponseEntity<String> validate(@RequestBody ValidateRequest validateRequest) {
    // TODO: use version
    String formattedSnippet = printScriptService.validate(validateRequest.snippet());
    return new ResponseEntity<>(formattedSnippet, HttpStatus.OK);
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
