package com.ingsis.jcli.printscript.consumers;

import org.austral.ingsis.redis.RedisStreamConsumer;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.stream.StreamReceiver;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class HelloConsumer extends RedisStreamConsumer<String> {

  public HelloConsumer(@Value("${stream.key}") String streamKey, @Value("${groups.product}") String groupId, @NotNull RedisTemplate<String, String> redis) {
    super(streamKey, groupId, redis);
  }

  @Override
  protected void onMessage(@NotNull ObjectRecord<String, String> objectRecord) {

  }

  @NotNull
  @Override
  protected StreamReceiver.StreamReceiverOptions<String, ObjectRecord<String, String>> options() {
    return null;
   // return StreamReceiver.StreamReceiverOptions.builder().pollTimeout(Duration.ofMillis(10000)).targetType(String).build();
  }
}
