package com.ingsis.jcli.printscript.printscript;

import static com.ingsis.jcli.printscript.common.TestUtils.getStringFromFile;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ingsis.jcli.printscript.common.FileType;
import com.ingsis.jcli.printscript.common.OperationType;
import com.ingsis.jcli.printscript.common.TestCaseData;
import com.ingsis.jcli.printscript.common.requests.AnalyzeRequest;
import com.ingsis.jcli.printscript.common.requests.ExecuteRequest;
import com.ingsis.jcli.printscript.common.requests.FormatRequest;
import com.ingsis.jcli.printscript.common.requests.ValidateRequest;
import com.ingsis.jcli.printscript.common.responses.ErrorResponse;
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

    when(printScriptService.format(input, rules, "1.1")).thenReturn(expected);

    FormatRequest req = new FormatRequest(input, rules, "1.1");

    mockMvc
        .perform(
            post("/printscript/format")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(req))
                .with(SecurityMockMvcRequestPostProcessors.jwt()))
        .andExpect(status().isOk())
        .andExpect(content().string(expected));
  }

  @Test
  void testAnalyze() throws Exception {
    String input = getStringFromFile(OperationType.ANALYZE, "test1", FileType.INPUT).get();
    String expected = getStringFromFile(OperationType.ANALYZE, "test1", FileType.OUTPUT).get();
    String rules = getStringFromFile(OperationType.ANALYZE, "test1", FileType.RULES).get();

    when(printScriptService.analyze(input, rules, "1.1")).thenReturn(expected);

    AnalyzeRequest req = new AnalyzeRequest(input, rules, "1.1");

    mockMvc
        .perform(
            post("/printscript/analyze")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(req))
                .with(SecurityMockMvcRequestPostProcessors.jwt()))
        .andExpect(status().isOk())
        .andExpect(content().string(expected));
  }

  @Test
  void testExecute() throws Exception {
    String input = getStringFromFile(OperationType.EXECUTE, "test1", FileType.INPUT).get();
    String expected = getStringFromFile(OperationType.EXECUTE, "test1", FileType.OUTPUT).get();

    when(printScriptService.execute(input, "1.1")).thenReturn(expected);

    ExecuteRequest req = new ExecuteRequest(input, "1.1");

    mockMvc
        .perform(
            post("/printscript/execute")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(req))
                .with(SecurityMockMvcRequestPostProcessors.jwt()))
        .andExpect(status().isOk())
        .andExpect(content().string(expected));
  }

  @Test
  void testValidate() throws Exception {
    List<TestCaseData> testCases =
        List.of(
            new TestCaseData("test1", false),
            new TestCaseData("test2", false),
            new TestCaseData("test3", true));
    for (TestCaseData testCase : testCases) {
      String input =
          getStringFromFile(OperationType.VALIDATE, testCase.getName(), FileType.INPUT).get();
      String expected =
          getStringFromFile(OperationType.VALIDATE, testCase.getName(), FileType.OUTPUT).get();

      if (testCase.isValid()) {
        when(printScriptService.validate(input, "1.1")).thenReturn(new ErrorResponse(""));
      } else {
        when(printScriptService.validate(input, "1.1")).thenReturn(new ErrorResponse(expected));
      }
      ValidateRequest req = new ValidateRequest(input, "1.1");
      if (testCase.isValid()) {
        mockMvc
            .perform(
                post("/printscript/validate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(req))
                    .with(SecurityMockMvcRequestPostProcessors.jwt()))
            .andExpect(status().isOk());
      } else {
        mockMvc
            .perform(
                post("/printscript/validate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(req))
                    .with(SecurityMockMvcRequestPostProcessors.jwt()))
            .andExpect(status().isBadRequest())
            .andExpect(content().json("{\"error\":\"" + expected + "\"}"));
      }
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
