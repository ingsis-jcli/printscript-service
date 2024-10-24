package com.ingsis.jcli.printscript.common.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ingsis.jcli.printscript.common.Generated;
import java.util.List;

@Generated
public record DefaultRules(@JsonProperty("rules") List<DefaultRule> rules) {}
