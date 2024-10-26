package com.ingsis.jcli.printscript.consumers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ingsis.jcli.printscript.consumers.requests.PendingTestCaseRun;
import java.util.List;
import org.junit.jupiter.api.Test;

class PendingTestCaseRunTest {

  @Test
  void testPendingTestCaseRunCreation() {
    Long id = 1L;
    String snippetName = "addOne";
    String url = "http://example.com/snippet";
    String version = "1.1";
    List<String> input = List.of("5");
    List<String> output = List.of("7");

    PendingTestCaseRun pendingTestCaseRun =
        new PendingTestCaseRun(id, snippetName, url, version, input, output);

    assertEquals(id, pendingTestCaseRun.id());
    assertEquals(snippetName, pendingTestCaseRun.snippetName());
    assertEquals(url, pendingTestCaseRun.url());
    assertEquals(version, pendingTestCaseRun.version());
    assertEquals(input, pendingTestCaseRun.input());
    assertEquals(output, pendingTestCaseRun.output());
  }
}
