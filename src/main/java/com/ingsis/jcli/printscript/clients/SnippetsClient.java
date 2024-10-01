package com.ingsis.jcli.printscript.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "snippets-api", url = "http://localhost:8080/")
public interface SnippetsClient {
  @RequestMapping(method = RequestMethod.GET, value = "/hello")
  String hello();
}
