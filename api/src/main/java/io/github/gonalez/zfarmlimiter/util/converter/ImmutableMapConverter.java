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

package io.github.gonalez.zfarmlimiter.util.converter;

import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class ImmutableMapConverter<K, V> implements ObjectConverter<Map<K, V>, ImmutableMap<K, V>> {
  @Override
  public Class<Map<K, V>> requiredType() {
    return (Class<Map<K, V>>) ((Class<?>) Map.class);
  }

  @Override
  public Class<ImmutableMap<K, V>> convertedType() {
    return (Class<ImmutableMap<K, V>>) ((Class<?>) ImmutableMap.class);
  }

  @Override
  public ImmutableMap<K, V> convert(Map<K, V> key) {
    return ImmutableMap.copyOf(key);
  }
}
