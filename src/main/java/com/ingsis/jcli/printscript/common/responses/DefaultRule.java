package com.ingsis.jcli.printscript.common.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DefaultRule(
  @JsonProperty("name") String name,
  @JsonProperty("isActive") boolean isActive,
  @JsonProperty("value") String value) {}