package com.ingsis.jcli.printscript.consumers;

import static com.ingsis.jcli.printscript.consumers.DeserializerUtil.deserializeIntoTestCase;

import com.ingsis.jcli.printscript.common.Generated;
import com.ingsis.jcli.printscript.common.responses.TestType;
import com.ingsis.jcli.printscript.consumers.products.TestCaseProduct;
import com.ingsis.jcli.printscript.producers.TestResultProducer;
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
public class TestCaseRunConsumer extends RedisStreamConsumer<String> {

  private final PrintScriptService printScriptService;
  private final TestResultProducer testResultProducer;

  @Autowired
  public TestCaseRunConsumer(
      @Value("${test_case_stream.key}") String streamKey,
      @Value("${test_case.groups.product}") String groupId,
      @NotNull RedisTemplate<String, String> redis,
      PrintScriptService printScriptService,
      TestResultProducer testResultProducer) {
    super(streamKey, groupId, redis);
    this.printScriptService = printScriptService;
    this.testResultProducer = testResultProducer;
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
    String testCase = objectRecord.getValue();
    if (testCase == null) {
      log.error("Received null testCase, check the serialization and JSON structure");
      return;
    }
    TestCaseProduct testCaseProduct = deserializeIntoTestCase(testCase);
    log.info("Processing testCase: " + testCaseProduct.getId());
    TestType type =
        printScriptService.runTestCase(
            testCaseProduct.getSnippetName(),
            testCaseProduct.getUrl(),
            testCaseProduct.getInput(),
            testCaseProduct.getOutput(),
            testCaseProduct.getVersion());
    log.info("Test result for testCase " + testCaseProduct.getId() + ": " + type);

    testResultProducer.returnResult(type, testCaseProduct.getId());
  }
}
