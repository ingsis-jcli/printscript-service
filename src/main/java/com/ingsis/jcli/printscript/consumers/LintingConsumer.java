package com.ingsis.jcli.printscript.consumers;


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
public class LintingConsumer extends RedisStreamConsumer<String> {

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
  protected StreamReceiver.StreamReceiverOptions<String, ObjectRecord<String, String>> options() {
    return StreamReceiver.StreamReceiverOptions.builder()
        .pollTimeout(Duration.ofMillis(10000))
        .targetType(String.class)
        .build();
  }

  @Override
  protected void onMessage(@NotNull ObjectRecord<String, String> objectRecord) {
    String message = objectRecord.getValue();
    log.info("Processing snippet message: " + message);
    System.out.println("Processing snippet: " + message);

//    LintRequest lintRequest = deserializeLintRequest(message);
//    SnippetDto snippet = lintRequest.snippetDto();
//    List<Rule> rules = lintRequest.ruleList();
//
//    String rulesConfig = convertRulesToConfig(rules);
//    String result = printScriptService.analyze(snippet.getContent(), rulesConfig, snippet.getVersion());
//
//    log.info("Linting result for snippet " + snippet.getName() + ": " + result);
  }

//  private LintRequest deserializeLintRequest(String message) {
//    return new Gson().fromJson(message, LintRequest.class);
//  }
//
//  private String convertRulesToConfig(List<Rule> rules) {
//    JsonObject rulesJson = new JsonObject();
//    for (Rule rule : rules) {
//      rulesJson.addProperty(rule.getName(), rule.getValue());
//    }
//    return rulesJson.toString();
//  }
}

