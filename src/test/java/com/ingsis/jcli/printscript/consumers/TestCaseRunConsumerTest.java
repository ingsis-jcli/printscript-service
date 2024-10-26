package com.ingsis.jcli.printscript.consumers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ingsis.jcli.printscript.services.PrintScriptService;
import java.time.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.stream.StreamReceiver;

class TestCaseRunConsumerTest {

  @Mock private PrintScriptService printScriptService;

  @Mock private RedisTemplate<String, String> redisTemplate;

  private TestCaseRunConsumer testCaseRunConsumer;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    testCaseRunConsumer =
        new TestCaseRunConsumer("testCaseStream", "testGroup", redisTemplate, printScriptService);
  }

  @Test
  void testOptions() {
    StreamReceiver.StreamReceiverOptions<String, ObjectRecord<String, String>> options =
        testCaseRunConsumer.options();
    assertEquals(Duration.ofMillis(10000), options.getPollTimeout());
    assertEquals(String.class, options.getTargetType());
  }

  //  @Test
  //  void testOnMessage() {
  //    Long id = 1L;
  //    String snippetName = "addOne";
  //    String url = "http://example.com/snippet";
  //    String version = "1.1";
  //    List<String> input = List.of("5");
  //    List<String> output = List.of("7");
  //
  //    String pendingTestCaseRun = String.format(
  //
  // "{\"id\":%d,\"snippetName\":\"%s\",\"url\":\"%s\",\"input\":
  // [\"%s\"],\"output\":[\"%s\"],\"version\":\"%s\"}",
  //        id, snippetName, url, input.get(0), output.get(0), version);
  //    ObjectRecord<String, String> record =
  //        ObjectRecord.create("testCaseStream", pendingTestCaseRun);
  //
  //    when(printScriptService.runTestCase(snippetName, url, input, output, version))
  //        .thenReturn(TestType.VALID);
  //
  //    testCaseRunConsumer.onMessage(record);
  //
  //    verify(printScriptService, times(1)).runTestCase(snippetName, url, input, output, version);
  //  }
}
