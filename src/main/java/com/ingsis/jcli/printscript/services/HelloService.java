package com.ingsis.jcli.printscript.services;

import com.ingsis.jcli.printscript.clients.PermissionsClient;
import com.ingsis.jcli.printscript.clients.SnippetsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

  private final PermissionsClient permissionsClient;
  private final SnippetsClient snippetsClient;

  @Autowired
  public HelloService(PermissionsClient permissionsClient, SnippetsClient snippetsClient) {
    this.permissionsClient = permissionsClient;
    this.snippetsClient = snippetsClient;
  }

  public String getHelloFromPermissionsServer() {
    return permissionsClient.hello();
  }

  public String getHelloFromSnippetsServer() {
    return snippetsClient.hello();
  }

  public String getHello() {
    return "Hello from printscript service!";
  }
}
