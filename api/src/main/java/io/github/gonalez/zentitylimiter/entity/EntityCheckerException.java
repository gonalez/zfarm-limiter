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
package io.github.gonalez.zentitylimiter.entity;

import static com.google.common.base.Preconditions.checkNotNull;

/** Exception thrown by the {@link EntityExtractor} when any ent checking error occurs. */
public class EntityCheckerException extends Exception {
  public static Builder newBuilder() {
    return new Builder();
  }

  private final EntityCheckerExceptionCode exceptionCode;

  public EntityCheckerException(EntityCheckerExceptionCode exceptionCode) {
    super();
    this.exceptionCode = checkNotNull(exceptionCode);
  }

  public EntityCheckerException(String message, EntityCheckerExceptionCode exceptionCode) {
    super(message);
    this.exceptionCode = exceptionCode;
  }

  public EntityCheckerExceptionCode getExceptionCode() {
    return exceptionCode;
  }

  public static class Builder {
    Builder() {}

    private String message;
    private EntityCheckerExceptionCode exceptionCode;

    public Builder withMessage(String message) {
      this.message = message;
      return this;
    }

    public Builder withExceptionCode(EntityCheckerExceptionCode exceptionCode) {
      this.exceptionCode = exceptionCode;
      return this;
    }

    public EntityCheckerException build() {
      checkNotNull(exceptionCode);
      if (message != null)
        return new EntityCheckerException(message, exceptionCode);
      return new EntityCheckerException(exceptionCode);
    }
  }
}