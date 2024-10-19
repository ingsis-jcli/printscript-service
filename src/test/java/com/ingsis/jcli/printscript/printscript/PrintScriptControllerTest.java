package com.ingsis.jcli.printscript.printscript;

import static com.ingsis.jcli.printscript.common.TestUtils.getStringFromFile;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ingsis.jcli.printscript.common.FileType;
import com.ingsis.jcli.printscript.common.OperationType;
import com.ingsis.jcli.printscript.common.requests.ValidateRequest;
import com.ingsis.jcli.printscript.controllers.PrintScriptController;
import com.ingsis.jcli.printscript.services.PrintScriptService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PrintScriptController.class)
@ActiveProfiles("test")
public class PrintScriptControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private PrintScriptService printScriptService;

  @MockBean private JwtDecoder jwtDecoder;

  @Test
  void testFormat() throws Exception {
    String input = getStringFromFile(OperationType.FORMAT, "test1", FileType.INPUT).get();
    String expected = getStringFromFile(OperationType.FORMAT, "test1", FileType.OUTPUT).get();
    String rules = getStringFromFile(OperationType.FORMAT, "test1", FileType.RULES).get();

    when(printScriptService.format(input, rules)).thenReturn(expected);

    mockMvc
        .perform(
            post("/printscript/format")
                .param("snippet", input)
                .param("config", rules)
                .with(SecurityMockMvcRequestPostProcessors.jwt()))
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
        .perform(
            post("/printscript/analyze")
                .param("snippet", input)
                .param("config", rules)
                .with(SecurityMockMvcRequestPostProcessors.jwt()))
        .andExpect(status().isOk())
        .andExpect(content().string(expected));
  }

  @Test
  void testExecute() throws Exception {
    String input = getStringFromFile(OperationType.EXECUTE, "test1", FileType.INPUT).get();
    String expected = getStringFromFile(OperationType.EXECUTE, "test1", FileType.OUTPUT).get();

    when(printScriptService.execute(input)).thenReturn(expected);

    mockMvc
        .perform(
            post("/printscript/execute")
                .param("snippet", input)
                .with(SecurityMockMvcRequestPostProcessors.jwt()))
        .andExpect(status().isOk())
        .andExpect(content().string(expected));
  }

  @Test
  void testValidate() throws Exception {
    List<String> cases = List.of("test1", "test2", "test3");
    for (String testCase : cases) {
      String input = getStringFromFile(OperationType.VALIDATE, testCase, FileType.INPUT).get();
      String expected = getStringFromFile(OperationType.VALIDATE, testCase, FileType.OUTPUT).get();

      when(printScriptService.validate(input)).thenReturn(expected);

      ValidateRequest req = new ValidateRequest(input, "1.1");

      mockMvc
          .perform(
              post("/printscript/validate")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(asJsonString(req))
                  .with(SecurityMockMvcRequestPostProcessors.jwt()))
          .andExpect(status().isOk())
          .andExpect(content().string(expected));
    }
  }

  public static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
