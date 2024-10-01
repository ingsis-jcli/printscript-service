package com.ingsis.jcli.printscript.hello;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ingsis.jcli.printscript.controllers.HelloController;
import com.ingsis.jcli.printscript.services.HelloService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(HelloController.class)
class HelloControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private HelloService helloService;

  @Test
  void testGetHello() throws Exception {
    when(helloService.getHello()).thenReturn("Hello from printscript service!");
    mockMvc
        .perform(get("/hello"))
        .andExpect(status().isOk())
        .andExpect(content().string("Hello from printscript service!"));
  }

  @Test
  void testGetHelloFromSnippets() throws Exception {
    when(helloService.getHelloFromSnippetsServer()).thenReturn("Hello from snippets service!");
    mockMvc
        .perform(get("/hello/snippets"))
        .andExpect(status().isOk())
        .andExpect(content().string("Hello from snippets service!"));
  }

  @Test
  void testGetHelloFromPermissions() throws Exception {
    when(helloService.getHelloFromPermissionsServer())
        .thenReturn("Hello from permissions service!");
    mockMvc
        .perform(get("/hello/permissions"))
        .andExpect(status().isOk())
        .andExpect(content().string("Hello from permissions service!"));
  }
}
