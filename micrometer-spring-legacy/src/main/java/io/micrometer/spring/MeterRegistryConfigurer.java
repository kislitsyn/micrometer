/**
 * Copyright 2017 Pivotal Software, Inc.
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
package io.micrometer.spring;

import io.micrometer.core.instrument.MeterRegistry;

/**
 * Defines callback methods to customize the Java-based configuration for
 * {@link io.micrometer.core.instrument.MeterRegistry} implementations.
 * <p>
 *
 * Add one or more of these configurers to the application context to customize
 * a registry.
 *
 * Configurers are guaranteed to be applied before any {@link io.micrometer.core.instrument.Meter}
 * is registered with the registry.
 *
 * @author Jon Schneider
 */
public interface MeterRegistryConfigurer {
    void configureRegistry(MeterRegistry registry);
}
