package com.ingsis.jcli.printscript.consumers;


import com.ingsis.jcli.printscript.common.requests.FormatRequest;
import com.ingsis.jcli.printscript.services.PrintScriptService;
import java.time.Duration;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.digester.Rule;
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
public class FormattingConsumer extends RedisStreamConsumer<String> {

  private final PrintScriptService printScriptService;

  @Autowired
  public FormattingConsumer(
      @Value("${formatting_stream.key}") String streamKey,
      @Value("${formatting.groups.product}") String groupId,
      @NotNull RedisTemplate<String, String> redis,
      PrintScriptService printScriptService) {
    super(streamKey, groupId, redis);
    this.printScriptService = printScriptService;
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
    String snippetId = objectRecord.getValue();
    log.info("Processing snippet: " + snippetId);
    System.out.println("Processing snippet: " + snippetId);
//    FormatRequest formatRequest = deserializeFormatRequest(objectRecord.getValue());
//    SnippetDto snippet = formatRequest.snippetDto();
//    List<Rule> rules = formatRequest.ruleList();
//
//    String rulesConfig = convertRulesToConfig(rules);
//    String result = printScriptService.format(snippet.getContent(), rulesConfig, snippet.getVersion());
//    log.info("Format result for snippet " + snippetId + ": " + result);
  }

//  private String convertRulesToConfig(List<Rule> rules) {
//    JsonObject rulesJson = new JsonObject();
//    for (Rule rule : rules) {
//      rulesJson.addProperty(rule.getName(), rule.getValue());
//    }
//    return rulesJson.toString();
//  }

//  private FormatRequest deserializeFormatRequest(String message) {
//    return new Gson().fromJson(message, FormatRequest.class);
//  }
}

