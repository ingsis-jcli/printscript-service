package com.ingsis.jcli.printscript.producers;

import com.ingsis.jcli.printscript.common.Generated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.RedisTemplate;

@Slf4j
@Generated
public abstract class JavaRedisStreamProducer {
  private final String streamKey;
  private final RedisTemplate<String, String> redis;

  protected JavaRedisStreamProducer(String streamKey, RedisTemplate<String, String> redis) {
    this.streamKey = streamKey;
    this.redis = redis;
  }

  public <T> RecordId emit(T value) {
    var record = StreamRecords.newRecord().ofObject(value).withStreamKey(streamKey);

    log.info("Emitting value: {}", value);

    return redis.opsForStream().add(record);
  }

  public String getStreamKey() {
    return streamKey;
  }

  public RedisTemplate<String, String> getRedis() {
    return redis;
  }
}
