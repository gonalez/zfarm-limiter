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
package io.github.gonalez.zfarmlimiter;

import com.google.auto.value.AutoValue;
import io.github.gonalez.zfarmlimiter.entity.EntityChecker;
import io.github.gonalez.zfarmlimiter.entity.RuleDescription;
import io.github.gonalez.zfarmlimiter.rule.RuleCollection;

/** Shared values for the {@link ZFarmLimiterPlugin} plugin. */
@AutoValue
public abstract class ZFarmLimiterPluginVariables {
  /** Creates a new builder, to create {@link ZFarmLimiterPluginVariables} instances. */
  public static Builder newBuilder() {
    return new AutoValue_ZFarmLimiterPluginVariables.Builder();
  }

  public abstract EntityChecker getEntityChecker();

  public abstract RuleCollection getRuleCollection();

  public abstract RuleDescription.Provider getRuleDescriptionProvider();

  /** Builder for {@link ZFarmLimiterPluginVariables}. */
  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setEntityChecker(EntityChecker entityChecker);

    public abstract Builder setRuleCollection(RuleCollection ruleCollection);

    public abstract Builder setRuleDescriptionProvider(RuleDescription.Provider ruleDescriptionProvider);

    public abstract ZFarmLimiterPluginVariables build();
  }
}
