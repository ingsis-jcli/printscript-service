package com.ingsis.jcli.printscript_service.controllers;

import com.ingsis.jcli.printscript_service.services.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello/")
public class HelloController {
  private final HelloService helloService;

  @Autowired
  public HelloController(HelloService helloService) {
    this.helloService = helloService;
  }

  @GetMapping("permissions")
  public String helloPermissions() {
    return helloService.getHelloFromPermissionsServer();
  }

  @GetMapping("snippets")
  public String helloSnippets() {
    return helloService.getHelloFromSnippetsServer();
  }

  @GetMapping
  public String hello() {
    return helloService.getHello();
  }
}
