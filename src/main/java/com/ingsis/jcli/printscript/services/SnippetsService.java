package com.ingsis.jcli.printscript.services;

import static com.ingsis.jcli.printscript.common.PrintScriptUtil.getInputStreamFromString;

import com.ingsis.jcli.printscript.clients.BucketClient;
import com.ingsis.jcli.printscript.common.exceptions.SnippetNotFoundException;
import java.io.InputStream;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SnippetsService {
  private final BucketClient bucketClient;

  @Autowired
  public SnippetsService(BucketClient bucketClient) {
    this.bucketClient = bucketClient;
  }

  public Optional<String> getSnippet(String name, String container) {
    ResponseEntity<String> response = bucketClient.getSnippet(name, container);
    if (response.hasBody()) {
      return Optional.of(response.getBody());
    }
    return Optional.empty();
  }

  public InputStream getSnippetStream(String name, String container) {
    Optional<String> content = getSnippet(name, container);
    if (content.isPresent()) {
      return getInputStreamFromString(content.get());
    }
    throw new SnippetNotFoundException(name, container);
  }
}
