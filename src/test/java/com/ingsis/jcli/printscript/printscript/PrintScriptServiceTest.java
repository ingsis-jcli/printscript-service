package com.ingsis.jcli.printscript.printscript;

import static com.ingsis.jcli.printscript.common.PrintScriptUtil.getInputStreamFromString;
import static com.ingsis.jcli.printscript.common.TestUtils.getStringFromFile;
import static org.mockito.Mockito.when;

import com.ingsis.jcli.printscript.clients.PermissionsClient;
import com.ingsis.jcli.printscript.clients.SnippetsClient;
import com.ingsis.jcli.printscript.common.FileType;
import com.ingsis.jcli.printscript.common.OperationType;
import com.ingsis.jcli.printscript.common.requests.Rule;
import com.ingsis.jcli.printscript.services.PrintScriptService;
import com.ingsis.jcli.printscript.services.SnippetsService;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PrintScriptServiceTest {
  @Mock private SnippetsClient snippetsClient;

  @Mock private PermissionsClient permissionsClient;

  @InjectMocks private PrintScriptService printScriptService;

  @Mock private SnippetsService snippetsService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testFormat() {
    String name = "test1";
    String url = "/format";
    Optional<String> input = getStringFromFile(OperationType.FORMAT, "test1", FileType.INPUT);
    Optional<String> expected = getStringFromFile(OperationType.FORMAT, "test1", FileType.OUTPUT);
    List<Rule> rules =
        List.of(
            new Rule(true, "declaration_space_before_colon", "true"),
            new Rule(true, "declaration_space_after_colon", "true"),
            new Rule(true, "assignment_space_after_equals", "true"),
            new Rule(true, "assignment_space_after_equals", "true"),
            new Rule(true, "println_new_lines_before_call", "0"));
    when(snippetsService.getSnippetStream(name, url))
        .thenReturn(getInputStreamFromString(input.get()));
    String output = printScriptService.format(name, url, rules, "1.1");
    System.out.println("OUTPUT: " + output);
    System.out.println("EXPECTED: " + expected.get());
  }

  @Test
  void testAnalyze() {
    Map<String, List<Rule>> testCases =
        Map.of(
            "test1", List.of(),
            "test2",
                List.of(
                    new Rule(true, "identifier_format", "camel case"),
                    new Rule(true, "mandatory-variable-or-literal-in-readInput", "true")));
    String url = "/analyze";
    for (Map.Entry<String, List<Rule>> testCase : testCases.entrySet()) {
      Optional<String> input =
          getStringFromFile(OperationType.ANALYZE, testCase.getKey(), FileType.INPUT);
      Optional<String> expected =
          getStringFromFile(OperationType.ANALYZE, testCase.getKey(), FileType.OUTPUT);
      Optional<String> rules =
          getStringFromFile(OperationType.ANALYZE, testCase.getKey(), FileType.RULES);
      when(snippetsService.getSnippetStream(testCase.getKey(), url))
          .thenReturn(getInputStreamFromString(input.get()));
      String output =
          printScriptService.analyze(testCase.getKey(), url, testCase.getValue(), "1.1");
      assert output.equals(expected.get());
    }
  }

  @Test
  void testExecute() {
    String url = "/execute";
    List<String> cases = List.of("test1", "test2");
    for (String caseName : cases) {
      Optional<String> input = getStringFromFile(OperationType.EXECUTE, caseName, FileType.INPUT);
      Optional<String> expected =
          getStringFromFile(OperationType.EXECUTE, caseName, FileType.OUTPUT);
      when(snippetsService.getSnippetStream(caseName, url))
          .thenReturn(getInputStreamFromString(input.get()));
      String output = printScriptService.execute(caseName, url, "1.1");
      assert output.equals(expected.get());
    }
  }
}
