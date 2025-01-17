/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with this
 * work for additional information regarding copyright ownership. The ASF
 * licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.apache.sling.feature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Groups a list of bundles {@code Artifact} and provides a means to sort them
 * based on start order. This class is not thread-safe.
 */
public class Bundles extends Artifacts {

    private static final long serialVersionUID = 5134067638024826299L;

    /**
     * Get the map of all bundles sorted by start order. The map is sorted
     * and iterating over the keys is done in start order. Bundles without a start
     * order (having the value 0) are returned last.
     * @return The map of bundles. The map is unmodifiable.
     */
    public Map<Integer, List<Artifact>> getBundlesByStartOrder() {
        final Map<Integer, List<Artifact>> startOrderMap = new TreeMap<>(new Comparator<Integer>() {

            @Override
            public int compare(final Integer o1, final Integer o2) {
                if ( o1.equals(o2) ) {
                    return 0;
                }
                if ( o1 == 0 ) {
                    return 1;
                }
                if ( o2 == 0 ) {
                    return -1;
                }
                return o1-o2;
            }
        });

        for (final Artifact bundle : this) {
            final int startOrder = bundle.getStartOrder();
            List<Artifact> list = startOrderMap.get(startOrder);
            if ( list == null ) {
                list = new ArrayList<>();
                startOrderMap.put(startOrder, list);
            }
            list.add(bundle);
        }
        return Collections.unmodifiableMap(startOrderMap);
    }
}
