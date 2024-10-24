package com.ingsis.jcli.printscript.consumers;

import com.ingsis.jcli.printscript.consumers.requests.PendingSnippetLint;
import com.ingsis.jcli.printscript.services.PrintScriptService;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.austral.ingsis.redis.RedisStreamConsumer;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.stream.StreamReceiver;
import org.springframework.stereotype.Component;

@Profile("!test")
@Slf4j
@Component
public class LintingConsumer extends RedisStreamConsumer<PendingSnippetLint> {

  private final PrintScriptService printScriptService;

  @Autowired
  public LintingConsumer(
      @Value("${linting_stream.key}") String streamKey,
      @Value("${linting.groups.product}") String groupId,
      @NotNull RedisTemplate<String, String> redis,
      PrintScriptService printScriptService) {
    super(streamKey, groupId, redis);
    this.printScriptService = printScriptService;
  }

  @NotNull
  @Override
  protected StreamReceiver.StreamReceiverOptions<String, ObjectRecord<String, PendingSnippetLint>>
      options() {
    return StreamReceiver.StreamReceiverOptions.builder()
        .pollTimeout(Duration.ofMillis(10000))
        .targetType(PendingSnippetLint.class)
        .build();
  }

  @Override
  protected void onMessage(@NotNull ObjectRecord<String, PendingSnippetLint> objectRecord) {
    PendingSnippetLint snippetLint = objectRecord.getValue();
    log.info("Processing snippet message: ");
    System.out.println(
        "Processing snippet: "
            + snippetLint.name()
            + " at "
            + snippetLint.url()
            + " with rules: "
            + snippetLint.rules());
    printScriptService.analyze(
        snippetLint.name(), snippetLint.url(), snippetLint.rules(), snippetLint.version());
  }
}
