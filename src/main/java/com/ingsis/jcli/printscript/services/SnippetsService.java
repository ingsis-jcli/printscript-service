package com.ingsis.jcli.printscript.services;

import static com.ingsis.jcli.printscript.utils.PrintScriptUtil.getInputStreamFromString;

import com.ingsis.jcli.printscript.clients.BucketRestClient;
import com.ingsis.jcli.printscript.clients.factories.BucketRestTemplateFactory;
import com.ingsis.jcli.printscript.common.exceptions.SnippetNotFoundException;
import java.io.InputStream;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SnippetsService {
  private final BucketRestClient bucketClient;

  @Autowired
  public SnippetsService(BucketRestTemplateFactory bucketRestTemplateFactory) {
    this.bucketClient = bucketRestTemplateFactory.createClient();
  }

  public Optional<String> getSnippet(String name, String container) {
    String snippet = bucketClient.getSnippet(name, container);
    return Optional.ofNullable(snippet);
  }

  public String getSnippetString(String name, String container) {
    Optional<String> content = getSnippet(name, container);
    if (content.isPresent()) {
      return content.get();
    }
    throw new SnippetNotFoundException(name, container);
  }

  public void updateSnippetInBucket(String content, String name, String container) {
    bucketClient.deleteSnippet(container, name);
    bucketClient.saveSnippet(container, name, content);
  }

  public InputStream getSnippetStreamFromString(String snippet) {
    return getInputStreamFromString(snippet);
  }

  public InputStream getSnippetStream(String name, String container) {
    Optional<String> content = getSnippet(name, container);
    if (content.isPresent()) {
      return getInputStreamFromString(content.get());
    }
    throw new SnippetNotFoundException(name, container);
  }
}
