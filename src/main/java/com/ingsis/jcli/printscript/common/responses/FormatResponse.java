package com.ingsis.jcli.printscript.common.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FormatResponse(
    @JsonProperty("content") String content, @JsonProperty("isCompliant") boolean isCompliant) {}
