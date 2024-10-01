package com.ingsis.jcli.printscript.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "permissions-api", url = "http://localhost:8081/")
public interface PermissionsClient {
  @RequestMapping(method = RequestMethod.GET, value = "/hello")
  String hello();

  @RequestMapping(method = RequestMethod.GET, value = "/permissions/")
  ResponseEntity<Boolean> hasPermission(
      @RequestParam("type") String type,
      @RequestParam("snippetId") Long snippetId,
      @RequestParam("userId") Long userId);
}
