package com.ingsis.jcli.printscript.common.responses;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ValidateResponse {
  private String error;

  public ValidateResponse(String error) {
    this.error = error;
  }

  public boolean isValid() {
    return error == null;
  }
}
