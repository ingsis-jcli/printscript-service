package com.ingsis.jcli.printscript.consumers.requests;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes({@JsonSubTypes.Type(value = PendingTestCaseProduct.class)})
public interface PendingTestCase {
  Long id();

  String snippetName();

  String url();

  String version();

  List<String> input();

  List<String> output();
}
