package com.ingsis.jcli.printscript.printscript;

import static com.ingsis.jcli.printscript.common.TestUtils.getStringFromFile;

import com.ingsis.jcli.printscript.clients.PermissionsClient;
import com.ingsis.jcli.printscript.clients.SnippetsClient;
import com.ingsis.jcli.printscript.common.FileType;
import com.ingsis.jcli.printscript.common.OperationType;
import com.ingsis.jcli.printscript.services.PrintScriptService;
import java.util.List;
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

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testFormat() {
    Optional<String> input = getStringFromFile(OperationType.FORMAT, "test1", FileType.INPUT);
    Optional<String> expected = getStringFromFile(OperationType.FORMAT, "test1", FileType.OUTPUT);
    Optional<String> rules = getStringFromFile(OperationType.FORMAT, "test1", FileType.RULES);
    String output = printScriptService.format(input.get(), rules.get(), "1.1");
    assert output.equals(expected.get());
  }

  @Test
  void testAnalyze() {
    List<String> cases = List.of("test1", "test2");
    for (String caseName : cases) {
      Optional<String> input = getStringFromFile(OperationType.ANALYZE, caseName, FileType.INPUT);
      Optional<String> expected =
          getStringFromFile(OperationType.ANALYZE, caseName, FileType.OUTPUT);
      Optional<String> rules = getStringFromFile(OperationType.ANALYZE, caseName, FileType.RULES);
      String output = printScriptService.analyze(input.get(), rules.get(), "1.1");
      assert output.equals(expected.get());
    }
  }

  @Test
  void testExecute() {
    List<String> cases = List.of("test1", "test2");
    for (String caseName : cases) {
      Optional<String> input = getStringFromFile(OperationType.EXECUTE, caseName, FileType.INPUT);
      Optional<String> expected =
          getStringFromFile(OperationType.EXECUTE, caseName, FileType.OUTPUT);
      String output = printScriptService.execute(input.get(), "1.1");
      assert output.equals(expected.get());
    }
  }
}
