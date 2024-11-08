package com.ingsis.jcli.printscript.clients;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ingsis.jcli.printscript.clients.factories.BucketRestTemplateFactory;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

class BucketRestTemplateFactoryTest {

  @Mock private RestTemplate restTemplate;

  @InjectMocks private BucketRestTemplateFactory bucketRestTemplateFactory;

  public BucketRestTemplateFactoryTest() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void createClient_ShouldReturnBucketRestClient() {
    BucketRestClient client = bucketRestTemplateFactory.createClient();
    assertNotNull(client, "The created BucketRestClient should not be null");
  }
}
