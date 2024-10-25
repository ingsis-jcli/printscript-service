package com.ingsis.jcli.printscript.consumers.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ingsis.jcli.printscript.common.requests.RuleDto;
import java.util.List;

public record PendingSnippetLint(
    @JsonProperty("snippetId") String snippetId,
    @JsonProperty("name") String name,
    @JsonProperty("url") String url,
    @JsonProperty("rules") List<RuleDto> rules,
    @JsonProperty("version") String version) {}
