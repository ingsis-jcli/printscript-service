package com.ingsis.jcli.printscript.controllers;

import com.ingsis.jcli.printscript.common.PermissionType;
import com.ingsis.jcli.printscript.dto.FormatterResponse;
import com.ingsis.jcli.printscript.services.PermissionService;
import com.ingsis.jcli.printscript.services.PrintScriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/printscript")
public class PrintScriptController {

  final PrintScriptService printScriptService;
  final PermissionService permissionService;

  @Autowired
  public PrintScriptController(
      PrintScriptService printScriptService, PermissionService permissionService) {
    this.printScriptService = printScriptService;
    this.permissionService = permissionService;
  }

  @GetMapping("/format")
  public ResponseEntity<FormatterResponse> format(
      @RequestParam Long userId,
      @RequestParam Long snippetId,
      @RequestParam String snippet,
      @RequestParam String config) {

    if (!permissionService.hasPermission(PermissionType.FORMAT, snippetId, userId)) {
      return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    } else {
      String formattedSnippet = printScriptService.format(snippet, config);
      FormatterResponse response = new FormatterResponse(snippetId, formattedSnippet);
      return new ResponseEntity<>(response, HttpStatus.OK);
    }
  }
}
