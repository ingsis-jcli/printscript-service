package com.ingsis.jcli.printscript.consumers.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ingsis.jcli.printscript.common.requests.Rule;
import java.util.List;

public record PendingSnippetLint(
    @JsonProperty("snippetId") String snippetId,
    @JsonProperty("name") String name,
    @JsonProperty("url") String url,
    @JsonProperty("rules") List<Rule> rules,
    @JsonProperty("version") String version) {}
