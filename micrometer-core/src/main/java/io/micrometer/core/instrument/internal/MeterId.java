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
package io.micrometer.core.instrument.internal;

import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.NamingConvention;
import io.micrometer.core.instrument.Tag;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.StreamSupport.stream;

public class MeterId implements Meter.Id {
    private final String name;
    private final Iterable<Tag> tags;
    private String baseUnit;
    private final String description;

    /**
     * Set after this id has been bound to a specific meter, effectively precluding it from use by a meter of a
     * different type.
     */
    private Meter.Type type;

    public MeterId(String name, Iterable<Tag> tags, String baseUnit, String description) {
        this.name = name;
        this.tags = tags;
        this.baseUnit = baseUnit;
        this.description = description;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Iterable<Tag> getTags() {
        return tags;
    }

    @Override
    public String getBaseUnit() {
        return baseUnit;
    }

    @Override
    public String getConventionName(NamingConvention namingConvention) {
        return namingConvention.name(name, type, baseUnit);
    }

    public String getDescription() {
        return description;
    }

    /**
     * Tags that are sorted by key and formatted
     */
    @Override
    public List<Tag> getConventionTags(NamingConvention namingConvention) {
        return stream(tags.spliterator(), false)
            .map(t -> Tag.of(namingConvention.tagKey(t.getKey()), namingConvention.tagValue(t.getValue())))
            .sorted(Comparator.comparing(Tag::getKey))
            .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "MeterId{" +
            "name='" + name + '\'' +
            ", tags=" + tags +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeterId meterId = (MeterId) o;
        return (name != null ? name.equals(meterId.name) : meterId.name == null) && (tags != null ? tags.equals(meterId.tags) : meterId.tags == null);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        return result;
    }

    public void setType(Meter.Type type) {
        this.type = type;
    }

    @Override
    public void setBaseUnit(String baseUnit) {
        this.baseUnit = baseUnit;
    }
}