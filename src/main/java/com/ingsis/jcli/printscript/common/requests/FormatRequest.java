package com.ingsis.jcli.printscript.common.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record FormatRequest(
    @JsonProperty("name") String name,
    @JsonProperty("url") String url,
    @JsonProperty("rules") List<RuleDto> rules,
    @JsonProperty("version") String version) {}
