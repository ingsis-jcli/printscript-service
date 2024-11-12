package com.ingsis.jcli.printscript.consumers;

import static com.ingsis.jcli.printscript.consumers.DeserializerUtil.deserializeIntoRequestProduct;

import com.ingsis.jcli.printscript.common.Generated;
import com.ingsis.jcli.printscript.common.responses.FormatResponse;
import com.ingsis.jcli.printscript.consumers.products.LintOrFormatRequestProduct;
import com.ingsis.jcli.printscript.producers.SnippetStatusUpdateProducer;
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

@Generated
@Profile("!test")
@Slf4j
@Component
public class FormattingConsumer extends RedisStreamConsumer<String> {

  private final PrintScriptService printScriptService;
  private final SnippetStatusUpdateProducer snippetStatusUpdateProducer;

  @Autowired
  public FormattingConsumer(
      @Value("${formatting_stream.key}") String streamKey,
      @Value("${formatting.groups.product}") String groupId,
      @NotNull RedisTemplate<String, String> redis,
      PrintScriptService printScriptService,
      SnippetStatusUpdateProducer snippetStatusUpdateProducer) {
    super(streamKey, groupId, redis);
    this.printScriptService = printScriptService;
    this.snippetStatusUpdateProducer = snippetStatusUpdateProducer;
  }

  @NotNull
  @Override
  protected StreamReceiver.StreamReceiverOptions<String, ObjectRecord<String, String>> options() {
    return StreamReceiver.StreamReceiverOptions.builder()
        .pollTimeout(Duration.ofMillis(10000))
        .targetType(String.class)
        .build();
  }

  @Override
  protected void onMessage(@NotNull ObjectRecord<String, String> objectRecord) {
    String formatRequest = objectRecord.getValue();
    if (formatRequest == null) {
      return;
    }

    log.info("Received FormatRequest value: {}", formatRequest);

    LintOrFormatRequestProduct formatRequestProduct = deserializeIntoRequestProduct(formatRequest);
    FormatResponse result =
        printScriptService.format(
            formatRequestProduct.getName(),
            formatRequestProduct.getUrl(),
            formatRequestProduct.getRules(),
            formatRequestProduct.getVersion());

    snippetStatusUpdateProducer.updateStatus(
        formatRequestProduct.getSnippetId(), "format", result.status());
  }
}
