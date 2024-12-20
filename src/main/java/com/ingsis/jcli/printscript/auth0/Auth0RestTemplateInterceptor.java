package com.ingsis.jcli.printscript.auth0;

import com.ingsis.jcli.printscript.common.Generated;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Generated
@Component
public class Auth0RestTemplateInterceptor implements ClientHttpRequestInterceptor {

  @Override
  public ClientHttpResponse intercept(
      HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
    RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
    if (requestAttributes != null) {
      HttpServletRequest httpServletRequest =
          ((ServletRequestAttributes) requestAttributes).getRequest();
      String authorizationHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
      if (authorizationHeader != null) {
        request.getHeaders().set(HttpHeaders.AUTHORIZATION, authorizationHeader);
      }
    }
    return execution.execute(request, body);
  }
}
