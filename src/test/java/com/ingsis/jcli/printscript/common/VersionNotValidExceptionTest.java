package com.ingsis.jcli.printscript.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.ingsis.jcli.printscript.common.exceptions.VersionNotValid;
import org.junit.jupiter.api.Test;

class VersionNotValidExceptionTest {

  @Test
  void testVersionNotValidExceptionMessage() {
    String version = "1.0-invalid";
    VersionNotValid exception =
        assertThrows(
            VersionNotValid.class,
            () -> {
              throw new VersionNotValid(version);
            });
    assertEquals("Version: " + version + " is not valid!", exception.getMessage());
  }
}
