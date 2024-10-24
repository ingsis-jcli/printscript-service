package com.ingsis.jcli.printscript.printscript;

import static com.ingsis.jcli.printscript.common.PrintScriptUtil.getInputStreamFromString;
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
import com.ingsis.jcli.printscript.common.requests.Rule;
import com.ingsis.jcli.printscript.common.requests.ValidateRequest;
import com.ingsis.jcli.printscript.common.responses.ErrorResponse;
import com.ingsis.jcli.printscript.controllers.PrintScriptController;
import com.ingsis.jcli.printscript.services.PrintScriptService;
import com.ingsis.jcli.printscript.services.SnippetsService;
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

  @MockBean private SnippetsService snippetsService;

  @MockBean private JwtDecoder jwtDecoder;

  @Test
  void testFormat() throws Exception {
    String url = "/format";
    String name = "test1";
    List<Rule> rules =
        List.of(
            new Rule(true, "declaration_space_before_colon", "true"),
            new Rule(true, "declaration_space_after_colon", "true"),
            new Rule(true, "declaration_space_before_equals", "true"),
            new Rule(true, "declaration_space_after_equals", "true"),
            new Rule(true, "println_new_lines_before_call", "0"));
    String input = getStringFromFile(OperationType.FORMAT, name, FileType.INPUT).get();
    String expected = getStringFromFile(OperationType.FORMAT, name, FileType.OUTPUT).get();

    when(printScriptService.format(name, url, rules, "1.1")).thenReturn(expected);
    when(snippetsService.getSnippetStream(url, name)).thenReturn(getInputStreamFromString(input));

    FormatRequest req = new FormatRequest(name, url, rules, "1.1");

    mockMvc
        .perform(
            post("/format")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(req))
                .with(SecurityMockMvcRequestPostProcessors.jwt()))
        .andExpect(status().isOk())
        .andExpect(content().string(expected));
  }

  @Test
  void testAnalyze() throws Exception {
    String url = "/analyze";
    String name = "test1";
    String input = getStringFromFile(OperationType.ANALYZE, name, FileType.INPUT).get();
    String expected = getStringFromFile(OperationType.ANALYZE, name, FileType.OUTPUT).get();
    List<Rule> rules = List.of();

    when(printScriptService.analyze(name, url, rules, "1.1")).thenReturn(expected);
    when(snippetsService.getSnippetStream(name, url)).thenReturn(getInputStreamFromString(input));

    AnalyzeRequest req = new AnalyzeRequest(name, url, rules, "1.1");

    mockMvc
        .perform(
            post("/analyze")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(req))
                .with(SecurityMockMvcRequestPostProcessors.jwt()))
        .andExpect(status().isOk())
        .andExpect(content().string(expected));
  }

  @Test
  void testExecute() throws Exception {
    String url = "/execute";
    String name = "test1";
    String input = getStringFromFile(OperationType.EXECUTE, name, FileType.INPUT).get();
    String expected = getStringFromFile(OperationType.EXECUTE, name, FileType.OUTPUT).get();

    when(printScriptService.execute(name, url, "1.1")).thenReturn(expected);
    when(snippetsService.getSnippetStream(name, url)).thenReturn(getInputStreamFromString(input));

    ExecuteRequest req = new ExecuteRequest(name, url, "1.1");

    mockMvc
        .perform(
            post("/execute")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(req))
                .with(SecurityMockMvcRequestPostProcessors.jwt()))
        .andExpect(status().isOk())
        .andExpect(content().string(expected));
  }

  @Test
  void testValidate() throws Exception {
    String url = "/validate";
    List<TestCaseData> testCases =
        List.of(
            new TestCaseData("test1", false, url),
            new TestCaseData("test2", false, url),
            new TestCaseData("test3", true, url));

    for (TestCaseData testCase : testCases) {
      String input =
          getStringFromFile(OperationType.VALIDATE, testCase.getName(), FileType.INPUT).get();
      String expected =
          getStringFromFile(OperationType.VALIDATE, testCase.getName(), FileType.OUTPUT).get();

      if (testCase.isValid()) {
        when(printScriptService.validate(testCase.getName(), url, "1.1"))
            .thenReturn(new ErrorResponse(""));
      } else {
        when(printScriptService.validate(testCase.getName(), url, "1.1"))
            .thenReturn(new ErrorResponse(expected));
      }

      when(snippetsService.getSnippetStream(testCase.getName(), url))
          .thenReturn(getInputStreamFromString(input));

      ValidateRequest req = new ValidateRequest(testCase.getName(), url, "1.1");

      if (testCase.isValid()) {
        mockMvc
            .perform(
                post("/validate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(req))
                    .with(SecurityMockMvcRequestPostProcessors.jwt()))
            .andExpect(status().isOk());
      } else {
        mockMvc
            .perform(
                post("/validate")
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
