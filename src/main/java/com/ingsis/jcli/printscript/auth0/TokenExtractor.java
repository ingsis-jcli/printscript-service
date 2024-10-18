package com.ingsis.jcli.printscript.auth0;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;

public class TokenExtractor {
  public static String extractToken(Authentication authentication) {
    if (authentication.getPrincipal() instanceof Jwt) {
      Jwt jwt = (Jwt) authentication.getPrincipal();
      return jwt.getTokenValue(); // Extract the token value
    }
    return null; // Or handle this case as needed
  }
}
