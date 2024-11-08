package com.ingsis.jcli.printscript.common.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record TestCaseRequest(
    @JsonProperty("snippetName") String snippetName,
    @JsonProperty("url") String url,
    @JsonProperty("version") String version,
    @JsonProperty("input") List<String> input,
    @JsonProperty("output") List<String> output) {}
