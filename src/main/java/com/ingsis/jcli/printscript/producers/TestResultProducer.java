package com.ingsis.jcli.printscript.producers;

import static com.ingsis.jcli.printscript.producers.SerializerUtil.serializeIntoTestCase;

import com.ingsis.jcli.printscript.common.responses.TestType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestResultProducer extends JavaRedisStreamProducer {

  @Autowired
  public TestResultProducer(
      @Value("${test_result_stream.key}") String streamKey, RedisTemplate<String, String> redis) {
    super(streamKey, redis);
  }

  public void returnResult(TestType type, Long testCaseId) {
    String message = serializeIntoTestCase(testCaseId, type);

    log.info("TestCaseResult message to emit: {}", message);

    emit(message);
  }
}
