/*
 * Copyright (C) 2020 The Baremaps Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.baremaps.osm.domain;

import com.baremaps.osm.handler.EntityConsumer;
import com.baremaps.osm.handler.EntityFunction;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import org.locationtech.jts.geom.Geometry;

/** Represents a way element in an OpenStreetMap dataset. */
public final class Way extends Element {

  private final List<Long> nodes;

  public Way(long id, Info info, Map<String, String> tags, List<Long> nodes) {
    super(id, info, tags);
    this.nodes = nodes;
  }

  public Way(long id, Info info, Map<String, String> tags, List<Long> nodes, Geometry geometry) {
    super(id, info, tags, geometry);
    this.nodes = nodes;
  }

  public List<Long> getNodes() {
    return nodes;
  }

  @Override
  public void visit(EntityConsumer consumer) throws Exception {
    consumer.match(this);
  }

  @Override
  public <T> T visit(EntityFunction<T> function) throws Exception {
    return function.match(this);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Way)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    Way way = (Way) o;
    return Objects.equals(nodes, way.nodes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), nodes);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Way.class.getSimpleName() + "[", "]")
        .add("nodes=" + nodes)
        .add("id=" + id)
        .toString();
  }
}
