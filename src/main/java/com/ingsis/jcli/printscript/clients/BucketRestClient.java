package com.ingsis.jcli.printscript.clients;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class BucketRestClient {

  private final RestTemplate restTemplate;
  private final String baseUrl;

  public BucketRestClient(RestTemplate restTemplate, String baseUrl) {
    this.restTemplate = restTemplate;
    this.baseUrl = baseUrl;
  }

  public String getSnippet(String key, String container) {
    String url = String.format("%s/asset/%s-%s", baseUrl, container, key);

    ResponseEntity<String> response =
        restTemplate.exchange(url, HttpMethod.GET, null, String.class);

    return response.getBody();
  }
}
