/*
 * Copyright 2022 - Gaston Gonzalez (Gonalez). and contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.gonalez.zfarmlimiter.rule;

import static com.google.common.base.Preconditions.checkState;

import io.github.gonalez.zfarmlimiter.registry.ObjectRegistry;
import io.github.gonalez.zfarmlimiter.util.converter.ObjectConverter;
import org.bukkit.configuration.file.YamlConfiguration;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.Map;

/**
 * A {@link YamlConfiguration} based rule serializer. The rule creation can fail if the configuration does not
 * have the necessary values, but the user can add default methods to the builder to add new fields, example,
 * if we add a new value to the rule,  "interval", we won't have this in the configuration, but we can call
 * this method with a default value in the {@code newBuilder} method.
 *
 * <p>{@code saveConfig}, will save the rule into the config file, useful to update new possible values
 * of the rule, as explained before.
 */
public class YamlConfigurationRuleSerializer extends FileWritingRuleSerializer {
  private final boolean saveConfig;

  public YamlConfigurationRuleSerializer(
      ObjectConverter.Registry objectConverterRegistry,  boolean checkForMissingFields,
      Path path, boolean saveConfig) throws IOException {
    super(objectConverterRegistry, checkForMissingFields, path);
    this.saveConfig = saveConfig;
  }

  @Override
  protected Class<? extends Rule> ruleType() {
    return Rule.class;
  }

  @Override
  protected RuleSerializerContext read(File file) {
    YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
    ObjectRegistry.Builder objectBuilder = ObjectRegistry.newBuilder();
    for (Map.Entry<String, Method> target : getRuleMethods().entrySet()) {
      String keyName = target.getKey();
      if (yamlConfiguration.contains(keyName)) {
        Object configKeyValue = yamlConfiguration.get(keyName);
        objectBuilder.add(
            keyName,
            (Class) target.getValue().getReturnType(),
            configKeyValue);
      }
    }
    objectBuilder.add("file", File.class, file);
    return RuleSerializerContext.of(objectBuilder.build());
  }

  @Override
  public Rule deserialize(RuleSerializerContext context, @Nullable Visitor visitor) throws IOException {
    File file = context.get(RULE_FILE_CONTEXT_VALUE_NAME, File.class);
    checkState(file != null,
        "File was not found on RuleSerializerContext," +
            " expected value name: " + RULE_FILE_CONTEXT_VALUE_NAME);
    YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
    Rule rule =
        super.deserialize(context,
            Visitor.of((rule1, valueName, valueType, value)
                ->
                    yamlConfiguration.set(valueName, value)));
    if (this.saveConfig) {
      yamlConfiguration.save(file);
    }
    return rule;
  }
}