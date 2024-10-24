package com.ingsis.jcli.printscript.snippets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.ingsis.jcli.printscript.clients.BucketClient;
import com.ingsis.jcli.printscript.common.exceptions.SnippetNotFoundException;
import com.ingsis.jcli.printscript.services.SnippetsService;
import java.io.InputStream;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class SnippetsServiceTest {

  @Mock private BucketClient bucketClient;

  @InjectMocks private SnippetsService snippetsService;

  @BeforeEach
  void setUp() {
    initMocks(this);
  }

  @Test
  void testGetSnippetSuccess() {
    String name = "testSnippet";
    String container = "testContainer";
    String expectedContent = "print('Hello World');";

    when(bucketClient.getSnippet(name, container))
        .thenReturn(new ResponseEntity<>(expectedContent, HttpStatus.OK));

    Optional<String> result = snippetsService.getSnippet(name, container);

    assertTrue(result.isPresent(), "Expected snippet content to be present");
    assertEquals(expectedContent, result.get(), "Expected snippet content to match");
    verify(bucketClient, times(1)).getSnippet(name, container);
  }

  @Test
  void testGetSnippetEmpty() {
    String name = "missingSnippet";
    String container = "testContainer";

    when(bucketClient.getSnippet(name, container))
        .thenReturn(new ResponseEntity<>(null, HttpStatus.NO_CONTENT));

    Optional<String> result = snippetsService.getSnippet(name, container);

    assertFalse(result.isPresent(), "Expected snippet content to be empty");
    verify(bucketClient, times(1)).getSnippet(name, container);
  }

  @Test
  void testGetSnippetStreamSuccess() {
    String name = "testSnippet";
    String container = "testContainer";
    String snippetContent = "print('Hello World');";

    when(bucketClient.getSnippet(name, container))
        .thenReturn(new ResponseEntity<>(snippetContent, HttpStatus.OK));

    InputStream result = snippetsService.getSnippetStream(name, container);

    assertNotNull(result, "Expected InputStream to be non-null");
    verify(bucketClient, times(1)).getSnippet(name, container);
  }

  @Test
  void testGetSnippetStreamThrowsExceptionWhenNotFound() {
    String name = "missingSnippet";
    String container = "testContainer";

    when(bucketClient.getSnippet(name, container))
        .thenReturn(new ResponseEntity<>(null, HttpStatus.NO_CONTENT));

    SnippetNotFoundException exception =
        assertThrows(
            SnippetNotFoundException.class,
            () -> snippetsService.getSnippetStream(name, container),
            "Expected SnippetNotFoundException to be thrown");

    assertEquals(
        exception.getMessage(), "Snippet names: " + name + " at " + container + " not found!");
    verify(bucketClient, times(1)).getSnippet(name, container);
  }
}
