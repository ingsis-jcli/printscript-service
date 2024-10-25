package com.ingsis.jcli.printscript.snippets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import com.ingsis.jcli.printscript.clients.BucketRestClient;
import com.ingsis.jcli.printscript.clients.factories.BucketRestTemplateFactory;
import com.ingsis.jcli.printscript.common.exceptions.SnippetNotFoundException;
import com.ingsis.jcli.printscript.services.SnippetsService;
import java.io.InputStream;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class SnippetsServiceTest {

  @Mock private BucketRestClient bucketRestClient;
  @Mock private BucketRestTemplateFactory bucketRestTemplateFactory;

  @InjectMocks private SnippetsService snippetsService;

  @BeforeEach
  void setUp() {
    openMocks(this);
    when(bucketRestTemplateFactory.createClient()).thenReturn(bucketRestClient);

    snippetsService = new SnippetsService(bucketRestTemplateFactory);
  }

  @Test
  void testGetSnippetSuccess() {
    String name = "testSnippet";
    String container = "testContainer";
    String expectedContent = "print('Hello World');";

    when(bucketRestClient.getSnippet(name, container)).thenReturn(expectedContent);
    Optional<String> result = snippetsService.getSnippet(name, container);

    assertTrue(result.isPresent(), "Expected snippet content to be present");
    assertEquals(expectedContent, result.get(), "Expected snippet content to match");
    verify(bucketRestClient, times(1)).getSnippet(name, container);
  }

  @Test
  void testGetSnippetEmpty() {
    String name = "missingSnippet";
    String container = "testContainer";

    when(bucketRestClient.getSnippet(name, container)).thenReturn(null);
    Optional<String> result = snippetsService.getSnippet(name, container);

    assertFalse(result.isPresent(), "Expected snippet content to be empty");
    verify(bucketRestClient, times(1)).getSnippet(name, container);
  }

  @Test
  void testGetSnippetStreamSuccess() {
    String name = "testSnippet";
    String container = "testContainer";
    String snippetContent = "print('Hello World');";

    when(bucketRestClient.getSnippet(name, container)).thenReturn(snippetContent);
    InputStream result = snippetsService.getSnippetStream(name, container);

    assertNotNull(result, "Expected InputStream to be non-null");
    verify(bucketRestClient, times(1)).getSnippet(name, container);
  }

  @Test
  void testGetSnippetStreamThrowsExceptionWhenNotFound() {
    String name = "missingSnippet";
    String container = "testContainer";

    when(bucketRestClient.getSnippet(name, container)).thenReturn(null);

    SnippetNotFoundException exception =
        assertThrows(
            SnippetNotFoundException.class,
            () -> snippetsService.getSnippetStream(name, container),
            "Expected SnippetNotFoundException to be thrown");

    assertEquals(
        exception.getMessage(), "Snippet names: " + name + " at " + container + " not found!");
    verify(bucketRestClient, times(1)).getSnippet(name, container);
  }
}
