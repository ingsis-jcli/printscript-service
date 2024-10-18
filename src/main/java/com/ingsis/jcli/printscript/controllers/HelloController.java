package com.ingsis.jcli.printscript.controllers;

import static com.ingsis.jcli.printscript.auth0.TokenExtractor.extractToken;

import com.ingsis.jcli.printscript.services.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello")
public class HelloController {
  private final HelloService helloService;

  @Autowired
  public HelloController(HelloService helloService) {
    this.helloService = helloService;
  }

  @GetMapping("/permissions")
  public String helloPermissions() {
    return helloService.getHelloFromPermissionsServer();
  }

  @GetMapping("/snippets")
  public String helloSnippets(Authentication authentication) {
    String token = extractToken(authentication);
    return helloService.getHelloFromSnippetsServer(token);
  }

  @GetMapping
  public String hello() {
    return helloService.getHello();
  }
}
