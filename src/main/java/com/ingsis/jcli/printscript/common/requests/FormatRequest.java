package com.ingsis.jcli.printscript.common.requests;

import java.util.List;

public record FormatRequest(String name, String url, List<Rule> rules, String version) {}
