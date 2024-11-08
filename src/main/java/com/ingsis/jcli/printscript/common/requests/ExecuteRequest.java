package com.ingsis.jcli.printscript.common.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ExecuteRequest(
    @JsonProperty("name") String name,
    @JsonProperty("url") String url,
    @JsonProperty("version") String version) {}
