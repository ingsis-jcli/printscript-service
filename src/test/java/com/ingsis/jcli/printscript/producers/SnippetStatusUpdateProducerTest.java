package com.ingsis.jcli.printscript.producers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.ingsis.jcli.printscript.common.responses.ProcessStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.core.RedisTemplate;

public class SnippetStatusUpdateProducerTest {

  @Mock private RedisTemplate<String, String> redisTemplate;

  @InjectMocks private SnippetStatusUpdateProducer snippetStatusUpdateProducer;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    snippetStatusUpdateProducer =
        new SnippetStatusUpdateProducer("snippetStatusUpdateStream", redisTemplate);
  }

  @Test
  void testUpdateStatus() {
    Long snippetId = 123L;
    String operation = "format";
    ProcessStatus status = ProcessStatus.NON_COMPLIANT;

    SnippetStatusUpdateProducer spyProducer = spy(snippetStatusUpdateProducer);

    RecordId mockRecordId = RecordId.of("1637846472718-0");
    doReturn(mockRecordId).when(spyProducer).emit(any());

    spyProducer.updateStatus(snippetId, operation, status);

    verify(spyProducer, times(1)).emit(any());
  }
}
