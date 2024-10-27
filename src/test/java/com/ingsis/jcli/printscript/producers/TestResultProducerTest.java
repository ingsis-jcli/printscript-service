package com.ingsis.jcli.printscript.producers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.ingsis.jcli.printscript.common.responses.TestType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.core.RedisTemplate;

public class TestResultProducerTest {

  @Mock private RedisTemplate<String, String> redisTemplate;

  @InjectMocks private TestResultProducer testResultProducer;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    testResultProducer = new TestResultProducer("testResultStream", redisTemplate);
  }

  @Test
  void testReturnResult() {
    TestType type = TestType.VALID;
    Long testCaseId = 123L;

    TestResultProducer spyProducer = spy(testResultProducer);

    RecordId mockRecordId = RecordId.of("1637846472718-0");
    doReturn(mockRecordId).when(spyProducer).emit(any());

    spyProducer.returnResult(type, testCaseId);

    verify(spyProducer, times(1)).emit(any());
  }
}
