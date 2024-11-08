package com.ingsis.jcli.printscript.common.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ErrorResponse(@JsonProperty("error") String error) {}
