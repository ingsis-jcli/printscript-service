package com.ingsis.jcli.printscript.consumers;

import com.ingsis.jcli.printscript.common.responses.TestType;
import com.ingsis.jcli.printscript.consumers.requests.PendingTestCaseProduct;
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
public class TestCaseRunConsumer extends RedisStreamConsumer<PendingTestCaseProduct> {

  private final PrintScriptService printScriptService;

  @Autowired
  public TestCaseRunConsumer(
      @Value("${test_case_stream.key}") String streamKey,
      @Value("${linting.groups.product}") String groupId,
      @NotNull RedisTemplate<String, String> redis,
      PrintScriptService printScriptService) {
    super(streamKey, groupId, redis);
    this.printScriptService = printScriptService;
  }

  @NotNull
  @Override
  protected StreamReceiver.StreamReceiverOptions<
          String, ObjectRecord<String, PendingTestCaseProduct>>
      options() {
    return StreamReceiver.StreamReceiverOptions.builder()
        .pollTimeout(Duration.ofMillis(10000))
        .targetType(PendingTestCaseProduct.class)
        .build();
  }

  @Override
  protected void onMessage(@NotNull ObjectRecord<String, PendingTestCaseProduct> objectRecord) {
    PendingTestCaseProduct testCase = objectRecord.getValue();
    log.info("Running test case with id: " + testCase.id());

    TestType type =
        printScriptService.runTestCase(
            testCase.snippetName(),
            testCase.url(),
            testCase.input(),
            testCase.output(),
            testCase.version());

    log.info("Test case with id: " + testCase.id() + " ran with result: " + type);
  }
}
