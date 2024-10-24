package com.ingsis.jcli.printscript.consumers.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.apache.tomcat.util.digester.Rule;

public record PendingSnippetFormat(
    @JsonProperty("snippetId") String snippetId,
    @JsonProperty("name") String name,
    @JsonProperty("url") String url,
    @JsonProperty("rules") List<Rule> rules) {}
