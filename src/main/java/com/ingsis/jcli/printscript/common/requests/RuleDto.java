package com.ingsis.jcli.printscript.common.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RuleDto(
    @JsonProperty("isActive") boolean isActive,
    @JsonProperty("name") String name,
    @JsonProperty("value") String value) {}
