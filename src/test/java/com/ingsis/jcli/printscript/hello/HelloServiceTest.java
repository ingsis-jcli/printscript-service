package com.ingsis.jcli.printscript.hello;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ingsis.jcli.printscript.clients.PermissionsClient;
import com.ingsis.jcli.printscript.clients.SnippetsClient;
import com.ingsis.jcli.printscript.services.HelloService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class HelloServiceTest {

  @Mock private SnippetsClient snippetsClient;

  @Mock private PermissionsClient permissionsClient;

  @InjectMocks private HelloService helloService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetHello() {
    String result = helloService.getHello();
    assertEquals("Hello from printscript service!", result);
  }

  @Test
  void testGetHelloFromPermissions() {
    when(permissionsClient.hello()).thenReturn("Hello from permissions service!");
    String result = helloService.getHelloFromPermissionsServer();
    assertEquals("Hello from permissions service!", result);
    verify(permissionsClient).hello();
  }

  @Test
  void testGetHelloFromSnippets() {
    when(snippetsClient.hello()).thenReturn("Hello from snippets service!");
    String result = helloService.getHelloFromSnippetsServer("1");
    assertEquals("Hello from snippets service!", result);
    verify(snippetsClient).hello();
  }
}
