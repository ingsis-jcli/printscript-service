package com.ingsis.jcli.printscript.consumers.products;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.ingsis.jcli.printscript.common.requests.RuleDto;
import java.lang.reflect.Type;

public class RuleDtoAdapter implements JsonDeserializer<RuleDto> {
  @Override
  public RuleDto deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {
    JsonObject jsonObject = json.getAsJsonObject();

    boolean isActive = jsonObject.get("isActive").getAsBoolean();
    String name = jsonObject.get("name").getAsString();
    String value = null;

    if (jsonObject.has("value") && !jsonObject.get("value").isJsonNull()) {
      JsonElement valueElement = jsonObject.get("value");

      if (valueElement.isJsonPrimitive()) {
        JsonPrimitive primitive = valueElement.getAsJsonPrimitive();
        if (primitive.isString()) {
          value = primitive.getAsString();
        } else if (primitive.isNumber()) {
          value = primitive.getAsNumber().toString();
        }
      }
    }

    return new RuleDto(isActive, name, value);
  }
}
