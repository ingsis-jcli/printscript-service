package com.ingsis.jcli.printscript.printscript;

import static com.ingsis.jcli.printscript.common.TestUtils.getStringFromFile;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.JsonObject;
import com.ingsis.jcli.printscript.common.FileType;
import com.ingsis.jcli.printscript.common.OperationType;
import com.ingsis.jcli.printscript.common.PermissionType;
import com.ingsis.jcli.printscript.controllers.PrintScriptController;
import com.ingsis.jcli.printscript.services.PermissionService;
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

  @MockBean private PermissionService permissionService;

  @Test
  void testFormat() throws Exception {
    Long userId = 1L;
    Long snippetId = 1L;
    String input = getStringFromFile(OperationType.FORMAT, "test1", FileType.INPUT).get();
    String expected = getStringFromFile(OperationType.FORMAT, "test1", FileType.OUTPUT).get();
    String rules = getStringFromFile(OperationType.FORMAT, "test1", FileType.RULES).get();
    when(permissionService.hasPermission(PermissionType.FORMAT, userId, snippetId))
        .thenReturn(true);
    when(printScriptService.format(input, rules)).thenReturn(expected);
    String formattedResponse =
        "{\"snippetId\":1,"
            + "\"formattedSnippet\":\"let a : number = 13 * 4;\\nlet b : string = \\\"hi\\\";\"}";
    mockMvc
        .perform(
            get("/printscript/format")
                .param("userId", userId.toString())
                .param("snippetId", snippetId.toString())
                .param("snippet", input)
                .param("config", rules))
        .andExpect(status().isOk())
        .andExpect(content().json(formattedResponse));
  }

  @Test
  void testAnalyze() throws Exception {
    Long userId = 1L;
    Long snippetId = 1L;
    String input = getStringFromFile(OperationType.ANALYZE, "test1", FileType.INPUT).get();
    String expected = getStringFromFile(OperationType.ANALYZE, "test1", FileType.OUTPUT).get();
    String rules = getStringFromFile(OperationType.ANALYZE, "test1", FileType.RULES).get();
    when(permissionService.hasPermission(PermissionType.ANALYZE, userId, snippetId))
        .thenReturn(true);
    when(printScriptService.analyze(input, rules)).thenReturn(expected);
    JsonObject responseJson = new JsonObject();
    responseJson.addProperty("snippetId", snippetId);
    responseJson.addProperty("report", expected);
    String response = responseJson.toString();
    mockMvc
        .perform(
            get("/printscript/analyze")
                .param("userId", userId.toString())
                .param("snippetId", snippetId.toString())
                .param("snippet", input)
                .param("config", rules))
        .andExpect(status().isOk())
        .andExpect(content().json(response));
  }
}
