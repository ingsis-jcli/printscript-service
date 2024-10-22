package com.ingsis.jcli.printscript.services;

import com.ingsis.jcli.printscript.clients.PermissionsClient;
import com.ingsis.jcli.printscript.clients.SnippetsClient;
import com.ingsis.jcli.printscript.producers.HelloProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

  private final PermissionsClient permissionsClient;
  private final SnippetsClient snippetsClient;
  private final HelloProducer helloProducer;

  @Autowired
  public HelloService(
      PermissionsClient permissionsClient,
      SnippetsClient snippetsClient,
      HelloProducer helloProducer) {
    this.permissionsClient = permissionsClient;
    this.snippetsClient = snippetsClient;
    this.helloProducer = helloProducer;
  }

  public String getHelloFromPermissionsServer() {
    return permissionsClient.hello();
  }

  public void publishHello() {
    helloProducer.produce("World");
  }

  public String getHelloFromSnippetsServer() {
    return snippetsClient.hello();
  }

  public String getHello() {
    return "Hello from printscript service!";
  }
}
