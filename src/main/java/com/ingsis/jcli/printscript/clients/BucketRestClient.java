package com.ingsis.jcli.printscript.clients;

import org.springframework.http.HttpEntity;
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

  public void saveSnippet(String container, String key, String content) {
    String url = String.format("%s/asset/%s-%s", baseUrl, container, key);
    HttpEntity<String> requestEntity = new HttpEntity<>(content);
    restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Void.class);
  }

  public void deleteSnippet(String container, String key) {
    String url = String.format("%s/asset/%s-%s", baseUrl, container, key);
    restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);
  }
}
