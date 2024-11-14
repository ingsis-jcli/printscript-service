package com.ingsis.jcli.printscript.producers;

import static com.ingsis.jcli.printscript.producers.SerializerUtil.serializeIntoStatusUpdate;

import com.ingsis.jcli.printscript.common.responses.ProcessStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SnippetStatusUpdateProducer extends JavaRedisStreamProducer {

  @Autowired
  public SnippetStatusUpdateProducer(
      @Value("${snippet_status_update.key}") String streamKey,
      RedisTemplate<String, String> redis) {
    super(streamKey, redis);
  }

  public void updateStatus(Long snippetId, String operation, ProcessStatus status) {
    String message = serializeIntoStatusUpdate(snippetId, operation, status);
    log.info("SnippetStatus message to emit: {}", message);
    emit(message);
  }
}
