package com.ingsis.jcli.printscript.printscript;

import static com.ingsis.jcli.printscript.common.TestUtils.getStringFromFile;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ingsis.jcli.printscript.common.FileType;
import com.ingsis.jcli.printscript.common.OperationType;
import com.ingsis.jcli.printscript.controllers.PrintScriptController;
import com.ingsis.jcli.printscript.services.PrintScriptService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PrintScriptController.class)
public class PrintScriptControllerTest {
  @Autowired private MockMvc mockMvc;

  @MockBean private PrintScriptService printScriptService;

  @Test
  void testFormat() throws Exception {
    String input = getStringFromFile(OperationType.FORMAT, "test1", FileType.INPUT).get();
    String expected = getStringFromFile(OperationType.FORMAT, "test1", FileType.OUTPUT).get();
    String rules = getStringFromFile(OperationType.FORMAT, "test1", FileType.RULES).get();
    when(printScriptService.format(input, rules)).thenReturn(expected);
    mockMvc
        .perform(get("/printscript/format").param("snippet", input).param("config", rules))
        .andExpect(status().isOk())
        .andExpect(content().string(expected));
  }

  @Test
  void testAnalyze() throws Exception {
    String input = getStringFromFile(OperationType.ANALYZE, "test1", FileType.INPUT).get();
    String expected = getStringFromFile(OperationType.ANALYZE, "test1", FileType.OUTPUT).get();
    String rules = getStringFromFile(OperationType.ANALYZE, "test1", FileType.RULES).get();
    when(printScriptService.analyze(input, rules)).thenReturn(expected);
    mockMvc
        .perform(get("/printscript/analyze").param("snippet", input).param("config", rules))
        .andExpect(status().isOk())
        .andExpect(content().string(expected));
  }

  @Test
  void testExecute() throws Exception {
    String input = getStringFromFile(OperationType.EXECUTE, "test1", FileType.INPUT).get();
    String expected = getStringFromFile(OperationType.EXECUTE, "test1", FileType.OUTPUT).get();
    when(printScriptService.execute(input)).thenReturn(expected);
    mockMvc
        .perform(get("/printscript/execute").param("snippet", input))
        .andExpect(status().isOk())
        .andExpect(content().string(expected));
  }
}
