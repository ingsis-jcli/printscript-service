package com.ingsis.jcli.printscript.producers;

import com.ingsis.jcli.printscript.consumers.HelloCreated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class HelloProducer extends JavaRedisStreamProducer {

  @Autowired
  public HelloProducer(
      @Value("${stream.key}") String streamKey, RedisTemplate<String, String> redis) {
    super(streamKey, redis);
  }

  public void produce(String name) {
    emit(new HelloCreated("Hello " + name + "!"));
  }
}
