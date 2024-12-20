package com.ingsis.jcli.printscript.clients;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

class BucketRestClientTest {

  @Mock private RestTemplate restTemplate;

  private BucketRestClient bucketRestClient;
  private final String baseUrl = "http://asset_service:8080/v1";

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    bucketRestClient = new BucketRestClient(restTemplate, baseUrl);
  }

  @Test
  void getSnippet_ShouldReturnSnippetContent() {
    String key = "snippet-key";
    String container = "snippet-container";
    String expectedContent = "Snippet content";
    String url = String.format("%s/asset/%s-%s", baseUrl, container, key);

    ResponseEntity<String> responseEntity = mock(ResponseEntity.class);
    when(responseEntity.getBody()).thenReturn(expectedContent);
    when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), eq(null), eq(String.class)))
        .thenReturn(responseEntity);

    String actualContent = bucketRestClient.getSnippet(key, container);

    assertEquals(expectedContent, actualContent);
    verify(restTemplate, times(1)).exchange(url, HttpMethod.GET, null, String.class);
  }

  @Test
  void saveSnippet_ShouldSendPutRequestWithContent() {
    String key = "snippet-key";
    String container = "snippet-container";
    String content = "Snippet content";
    String url = String.format("%s/asset/%s-%s", baseUrl, container, key);

    HttpEntity<String> requestEntity = new HttpEntity<>(content);
    when(restTemplate.exchange(eq(url), eq(HttpMethod.PUT), eq(requestEntity), eq(Void.class)))
        .thenReturn(null);

    bucketRestClient.saveSnippet(container, key, content);

    verify(restTemplate, times(1)).exchange(url, HttpMethod.PUT, requestEntity, Void.class);
  }

  @Test
  void deleteSnippet_ShouldSendDeleteRequest() {
    String key = "snippet-key";
    String container = "snippet-container";
    String url = String.format("%s/asset/%s-%s", baseUrl, container, key);

    when(restTemplate.exchange(eq(url), eq(HttpMethod.DELETE), eq(null), eq(Void.class)))
        .thenReturn(null);

    bucketRestClient.deleteSnippet(container, key);

    verify(restTemplate, times(1)).exchange(url, HttpMethod.DELETE, null, Void.class);
  }
}
