package com.ingsis.jcli.printscript.config;

import com.ingsis.jcli.printscript.auth0.Auth0RestTemplateInterceptor;
import com.ingsis.jcli.printscript.server.CorrelationIdInterceptor;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

  @Bean
  public RestTemplate restTemplate(Auth0RestTemplateInterceptor authInterceptor) {
    RestTemplate restTemplate = new RestTemplate();
    CorrelationIdInterceptor correlationIdInterceptor = new CorrelationIdInterceptor();
    List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
    interceptors.add(authInterceptor);
    interceptors.add(correlationIdInterceptor);
    restTemplate.setInterceptors(interceptors);

    return restTemplate;
  }
}
